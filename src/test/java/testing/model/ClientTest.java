package testing.model;
import org.junit.BeforeClass;
import org.junit.Test;
import system.model.Client;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Set;

import static org.junit.Assert.*;


public class ClientTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testToString() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Client client = new Client(1, "fullName", 1, 112, "sdf444", "1234567890");
        System.out.println(client.toString());
        String expected = new StringBuilder()
                .append("Client(id=").append(client.getId())
                .append(", fullName=").append(client.getFullName())
                .append(", regionID=").append(client.getRegionID())
                .append(", dataplanID=").append(client.getDataplanID())
                .append(", passportCode=").append(client.getPassportCode())
                .append(", phoneNumber=").append(client.getPhoneNumber())
                .append(")")
                .toString();
        String actual = outContent.toString().trim().replace("\r", "");
        assertEquals(expected, actual);
    }

    //region [SETTERS]
    @Test
    public void setId() throws NoSuchFieldException, IllegalAccessException {
        Client client = new Client();
        client.setId(22);
        Field field = client.getClass().getDeclaredField("id");
        field.setAccessible(true);
        assertEquals(22, field.get(client));
    }

    @Test
    public void setFullName() throws NoSuchFieldException, IllegalAccessException {
        Client client = new Client();
        client.setFullName("client");
        Field field = client.getClass().getDeclaredField("fullName");
        field.setAccessible(true);
        assertEquals("client", field.get(client));
    }

    @Test
    public void setRegionID() throws NoSuchFieldException, IllegalAccessException {
        Client client = new Client();
        client.setRegionID(1);
        Field field = client.getClass().getDeclaredField("regionID");
        field.setAccessible(true);
        assertEquals(1, field.get(client));
    }

    @Test
    public void setDataplanID() throws NoSuchFieldException, IllegalAccessException {
        Client client = new Client();
        client.setDataplanID(1);
        Field field = client.getClass().getDeclaredField("dataplanID");
        field.setAccessible(true);
        assertEquals(1, field.get(client));
    }

    @Test
    public void setPassportCode() throws NoSuchFieldException, IllegalAccessException {
        Client client = new Client();
        client.setPassportCode("code");
        Field field = client.getClass().getDeclaredField("passportCode");
        field.setAccessible(true);
        assertEquals("code", field.get(client));
    }

    @Test
    public void setPhoneNumber() throws NoSuchFieldException, IllegalAccessException {
        Client client = new Client();
        client.setPhoneNumber("1234567890");
        Field field = client.getClass().getDeclaredField("phoneNumber");
        field.setAccessible(true);
        assertEquals("1234567890", field.get(client));
    }
    //endregion

    //region [GETTERS]
    @Test
    public void getId() throws NoSuchFieldException, IllegalAccessException {
        Client client = new Client();
        Field field = client.getClass().getDeclaredField("id");
        field.setAccessible(true);
        field.set(client, 4);
        int actual = client.getId();
        assertEquals(4, actual);
    }

    @Test
    public void getFullName() throws NoSuchFieldException, IllegalAccessException {
        Client client = new Client();
        Field field = client.getClass().getDeclaredField("fullName");
        field.setAccessible(true);
        field.set(client, "client");
        String actual = client.getFullName();
        assertEquals("client", actual);
    }

    @Test
    public void getRegionID() throws NoSuchFieldException, IllegalAccessException {
        Client client = new Client();
        Field field = client.getClass().getDeclaredField("regionID");
        field.setAccessible(true);
        field.set(client, 1);
        int actual = client.getRegionID();
        assertEquals(1, actual);
    }

    @Test
    public void getDataplanID() throws NoSuchFieldException, IllegalAccessException {
        Client client = new Client();
        Field field = client.getClass().getDeclaredField("dataplanID");
        field.setAccessible(true);
        field.set(client, 1);
        int actual = client.getDataplanID();
        assertEquals(1, actual);
    }

    @Test
    public void getPassportCode() throws NoSuchFieldException, IllegalAccessException {
        Client client = new Client();
        Field field = client.getClass().getDeclaredField("passportCode");
        field.setAccessible(true);
        field.set(client, "code");
        String actual = client.getPassportCode();
        assertEquals("code", actual);
    }

    @Test
    public void getPhoneNumber() throws NoSuchFieldException, IllegalAccessException {
        Client client = new Client();
        Field field = client.getClass().getDeclaredField("phoneNumber");
        field.setAccessible(true);
        field.set(client, "1234567890");
        String actual = client.getPhoneNumber();
        assertEquals("1234567890", actual);
    }
    //endregion

    //region [CONSTRAINTS]
    @Test
    public void idIsInvalid() {
        Client client = new Client(-3,"name", 1, 1, "code", "1234567890");
        Set<ConstraintViolation<Client>> constraintViolationSet = validator.validate(client);
        assertEquals(2, constraintViolationSet.size());
    }

    @Test
    public void fullNameIsNull() {
        Client client = new Client(1,null, 1, 1, "code", "1234567890");
        Set<ConstraintViolation<Client>> constraintViolationSet = validator.validate(client);
        assertEquals(1, constraintViolationSet.size());
    }

    @Test
    public void fullNameIsEmpty() {
        Client client = new Client(1,"", 1, 1, "code", "1234567890");
        Set<ConstraintViolation<Client>> constraintViolationSet = validator.validate(client);
        assertEquals(1, constraintViolationSet.size());
    }

    @Test
    public void regionIDIsInvalid() {
        Client client = new Client(1,"name", -52, 1, "code", "1234567890");
        Set<ConstraintViolation<Client>> constraintViolationSet = validator.validate(client);
        assertEquals(2, constraintViolationSet.size());
    }

    @Test
    public void dataplanIDIsInvalid() {
        Client client = new Client(1,"name", 1, -2, "code", "1234567890");
        Set<ConstraintViolation<Client>> constraintViolationSet = validator.validate(client);
        assertEquals(2, constraintViolationSet.size());
    }

    @Test
    public void passportCodeIsNull() {
        Client client = new Client(1,"name", 1, 1, null, "1234567890");
        Set<ConstraintViolation<Client>> constraintViolationSet = validator.validate(client);
        assertEquals(1, constraintViolationSet.size());
    }

    @Test
    public void passportCodeIsEmpty() {
        Client client = new Client(1,"name", 1, 1, "", "1234567890");
        Set<ConstraintViolation<Client>> constraintViolationSet = validator.validate(client);
        assertEquals(1, constraintViolationSet.size());
    }

    @Test
    public void phoneNumberIsNull() {
        Client client = new Client(1,"name", 1, 1, "code", null);
        Set<ConstraintViolation<Client>> constraintViolationSet = validator.validate(client);
        assertEquals(1, constraintViolationSet.size());
    }

    @Test
    public void phoneNumberIsEmpty() {
        Client client = new Client(1,"name", 1, 1, "code", "");
        Set<ConstraintViolation<Client>> constraintViolationSet = validator.validate(client);
        assertEquals(1, constraintViolationSet.size());
    }
    //endregion
}