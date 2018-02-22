package system.dao;

import system.model.Client;
import system.model.NumberPlan;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.text.WordUtils.capitalizeFully;

import java.util.HashSet;
import java.util.Random;
/**
 * This class contains methods that generate some random data that will fill the Clients table.
 */
public class DataGenerator {
    private static final String[] OPERATOR_CODES = new String[]{"097", "050", "066", "099", "039", "063", "091"};
    private static final Random random = new Random();
    private static HashSet<NumberPlan> numberPlanHashSet = new HashSet<>();

    /**
     * Builds a sequences of alphabetic symbols to imitate client's first name and last name.
     * @return generated full name.
     */
    public String generateFullName() {
        String firstName = randomAlphabetic(8);
        String lastName = randomAlphabetic(8);
        return capitalizeFully(firstName + " " + lastName);
    }

    /**
     * Builds a new sequence of alphabet and numeric symbols for passport code of cellular system's client.
     * @return generated passport code.
     */
    public String generatePassportCode() {
        return randomAlphanumeric(6).toUpperCase();
    }

    /**
     * @return Dataplan id in cellular system (1-9).
     */
    public int generateDataplanId() {
        return random.ints(1, 1, 10).findFirst().getAsInt();
    }

    /**
     * @return region id in cellular system (1-24).
     */
    public int generateRegionId() {
        return random.ints(1, 1, 25).findFirst().getAsInt();
    }

    /**
     * Operator code is chosen randomly from array with codes.
     * The util of the phone number is generated with the help of RandomStringUtils.
     * @return generated phone number.
     * {@link DataGenerator#OPERATOR_CODES}
     */
    public String generatePhoneNumber() {
        int codePosition = random.nextInt(OPERATOR_CODES.length);
        return OPERATOR_CODES[codePosition] + randomNumeric(7);
    }

    /**
     * Generates clients that will be inserted into database.
     * At the same time NumberPlan entity is generated in order to be passed to client data attributes.
     * @return set of clients.
     */
    public static HashSet<Client> generateClientsInfo(int amountOfClients) {
        HashSet<Client> clientsHashSet = new HashSet<>();
        DataGenerator dataGenerator = new DataGenerator();
        NumberPlan numberPlanEntity;
        int clientId = 1;
        for (int i = 0; i < amountOfClients; i++) {
            numberPlanEntity = new NumberPlan(dataGenerator.generateDataplanId(), dataGenerator.generatePhoneNumber());
            numberPlanHashSet.add(numberPlanEntity);

            clientsHashSet.add(new Client(clientId,
                                        dataGenerator.generateFullName(),
                                        dataGenerator.generateRegionId(),
                                        numberPlanEntity.getDataplanID(),
                                        dataGenerator.generatePassportCode(),
                                        numberPlanEntity.getPhoneNumber()));
            clientId++;
            }
        return clientsHashSet;
    }

    static HashSet<NumberPlan> getNumberPlansHashSet() {
        return numberPlanHashSet;
    }
}