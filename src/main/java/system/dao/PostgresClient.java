package system.dao;

import lombok.Getter;
import system.model.Client;
import system.model.ClientDisplayInfo;
import system.model.DataPlanStatistics;
import system.model.NumberPlan;

import java.sql.*;
import java.util.*;

import static system.dao.DataGenerator.generateClientsInfo;
import static system.dao.DataGenerator.getNumberPlansHashSet;

/**
 * This class provides methods for creating connection to database and making queries to insert generated data.
 * Method createPrimaryData() must be invoked only once before starting the server with web application in order to fill
 * the database with primary data.
 */
public class PostgresClient implements AutoCloseable {

    @Getter
    private static Connection connection;
    private static PreparedStatement preparedStatement;

    public PostgresClient(String url, String username, String password) {
        connectToDb(url, username, password);
    }

    /**
     * Generates clients data and inserts it to database.
     */
    public void createPrimaryData(String url, String username, String password) {
        connectToDb(url, username, password);
        HashSet<Client> clients = generateClientsInfo(2000);
        HashSet<NumberPlan> numberPlans = getNumberPlansHashSet();
        insertClients(clients);
        insertNumberPlans(numberPlans);
        insertDataplanStatistics(url, username, password);
        System.out.println("Data successfully created.");
    }

    /**
     * Creates a connection to database.
     *
     * @throws SQLException if the resource cannot be reached.
     */
    public static void connectToDb(String url, String username, String password) {
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection successfully opened.");
        } catch (SQLException e) {
            System.out.println("Error occured while opening connection to database: " + e.getMessage());
        }
    }

    //region Insert Primary Data
    public static void insertClients(HashSet<Client> clientHashSet) {
        try {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO clients(full_name, region_id, dataplan_id, passport_code, phone_number)" +
                            " VALUES(?,?,?,?,?)");
            for (Client client : clientHashSet) {
                preparedStatement.setString(1, client.getFullName());
                preparedStatement.setInt(2, client.getRegionID());
                preparedStatement.setInt(3, client.getDataplanID());
                preparedStatement.setString(4, client.getPassportCode());
                preparedStatement.setString(5, client.getPhoneNumber());
                preparedStatement.executeUpdate();
            }
            System.out.println("Data successfully inserted into 'clients' table.");
        } catch (SQLException e) {
            System.out.println("Error occured while inserting data to 'clients' table: " + e.getMessage());
        }
    }

    public static void insertNumberPlans(HashSet<NumberPlan> numberPlanHashSet) {
        try {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO numbers_plans(phone_number, dataplan_id) VALUES(?,?)");
            for (NumberPlan numberPlan : numberPlanHashSet) {
                preparedStatement.setString(1, numberPlan.getPhoneNumber());
                preparedStatement.setInt(2, numberPlan.getDataplanID());
                preparedStatement.executeUpdate();
            }
            System.out.println("Data successfully inserted into 'numbers_plans' table.");
        } catch (SQLException e) {
            System.out.println("Error occured while inserting data to 'clients' table: " + e.getMessage());
        }
    }

    /**
     * Selects data from clients table in order to fill table for computing dataplan statistics.
     */
    public static void insertDataplanStatistics(String url, String username, String password) {
        connectToDb(url, username, password);
        HashSet<DataPlanStatistics> dataPlanStatisticsHashSet = new HashSet<>();
        try {
            String query = "SELECT clients.dataplan_id, clients.region_id, regions.name,\n" +
                    "    COUNT(dataplan_id)\n" +
                    "    FROM clients\n" +
                    "    INNER JOIN regions ON clients.region_id = regions.id\n" +
                    "    GROUP BY clients.dataplan_id, clients.region_id, regions.name\n" +
                    "    ORDER BY dataplan_id ASC";
            preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                dataPlanStatisticsHashSet.add(new DataPlanStatistics(
                        rs.getInt("region_id"),
                        rs.getString("name"),
                        rs.getInt("dataplan_id"))
                );
            }
            dataPlanStatisticsHashSet.forEach(dpsItem -> {
                try {
                    preparedStatement = connection.prepareStatement("INSERT INTO dataplan_statistics(region_id, region_name, dataplan_id) VALUES(?,?,?)");
                    preparedStatement.setInt(1, dpsItem.getRegionID());
                    preparedStatement.setString(2, dpsItem.getRegionName());
                    preparedStatement.setInt(3, dpsItem.getDataplanID());
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("Error occured while inserting data to 'dataplan_statistics' table: " + e.getMessage());
                }
            });
            System.out.println("Data successfully inserted into 'dataplan_statistics' table.");
        } catch (SQLException e) {
            System.out.println("Error occured while working with database: " + e.getMessage());
        }
    }

    //endregion

    public static void updateClient(Client client) {
        try {
            preparedStatement = connection.prepareStatement(
                    "UPDATE clients SET full_name=?, region_id=?, dataplan_id=?," +
                            "passport_code=?, phone_number=? WHERE id=?");
            preparedStatement.setString(1, client.getFullName());
            preparedStatement.setInt(2, client.getRegionID());
            preparedStatement.setInt(3, client.getDataplanID());
            preparedStatement.setString(4, client.getPassportCode());
            preparedStatement.setString(5, client.getPhoneNumber());
            preparedStatement.setInt(6, client.getId());
            preparedStatement.executeUpdate();
            System.out.println("Client was successfully updated.");
        } catch (SQLException e) {
            System.out.println("Error occured while updating client: " + e.getMessage());
        }
    }

    public static void deleteClient(Client client) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM clients WHERE id=?");
            preparedStatement.setInt(1, client.getId());
            preparedStatement.executeUpdate();
            System.out.println("Client was successfully deleted.");
        } catch (SQLException e) {
            System.out.println("Error occured while deleting client: " + e.getMessage());
        }
    }

    /**
     * Closes the resource, relinquishing any underlying resources.
     *
     * @throws SQLException if this resource cannot be closed
     */
    @Override
    public void close() throws SQLException {
        connection.close();
        System.out.println("Connection closed.");
    }

    /**
     * Creates query for country region.
     *
     * @return dataplan id that will be used to choose the color code for certain region on the map.
     */
    public static int computeDataplanIdForRegionColor(int mapRegionId, String url, String username, String password) {
        connectToDb(url, username, password);
        HashMap<Integer, Integer> resultMap = new HashMap<>();
        int dataplanId = 0;
        try {
            String query = "SELECT dataplan_id, region_id, COUNT(dataplan_id) " +
                    "FROM clients " +
                    "WHERE region_id = " + mapRegionId +
                    "GROUP BY dataplan_id, region_id";
            preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                resultMap.put(rs.getInt("dataplan_id"), rs.getInt("count"));
            }
            dataplanId = computeDataplanIdWithMaxValue(resultMap);
        } catch (SQLException e) {
            System.out.println("Error occured while selecting data from 'clients' table" + e.getMessage());
        }
        return dataplanId;
    }

    /**
     * @param map Result map that is consisted of dataplan id and the number of rows of that id in the database.
     * @return value of dataplan id with the greatest amount of rows in a table.
     */
    public static int computeDataplanIdWithMaxValue(HashMap<Integer, Integer> map) {
        return Collections.max(map.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
    }

    //region CRUD operations for AJAX requests
    public static ArrayList<ClientDisplayInfo> getAllClients(String url, String username, String password) {
        connectToDb(url, username, password);
        ArrayList<ClientDisplayInfo> clients = new ArrayList<>();
        try {
            String query = "SELECT clients.id, clients.full_name, clients.passport_code, clients.phone_number, regions.name, dataplans.title\n" +
                    "FROM clients\n" +
                    "INNER JOIN regions ON clients.region_id = regions.id\n" +
                    "INNER JOIN dataplans ON clients.dataplan_id = dataplans.id\n" +
                    "GROUP BY clients.full_name, clients.passport_code, clients.phone_number, regions.name, dataplans.title\n" +
                    "ORDER BY dataplan_id ASC";
            preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                clients.add(new ClientDisplayInfo(
                        rs.getInt("id"),
                        rs.getString("full_name"),
                        rs.getString("passport_code"),
                        rs.getString("phone_number"),
                        rs.getString("name"),
                        rs.getString("title")));
            }
        } catch (SQLException e) {
            System.out.println("Error occured while selecting data from 'clients' table: " + e.getMessage());
        }
        return clients;
    }

    public static void insertClient(Client client) {
        try {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO clients(full_name, region_id, dataplan_id, passport_code, phone_number) VALUES(?,?,?,?,?)");
            preparedStatement.setString(1, client.getFullName());
            preparedStatement.setInt(2, client.getRegionID());
            preparedStatement.setInt(3, client.getDataplanID());
            preparedStatement.setString(4, client.getPassportCode());
            preparedStatement.setString(5, client.getPhoneNumber());
            preparedStatement.executeUpdate();
            System.out.println(client.toString());
        } catch (SQLException e) {
            System.out.println("Error occured while inserting data to 'clients' table: " + e.getMessage());
        }
    }
    //endregion
}