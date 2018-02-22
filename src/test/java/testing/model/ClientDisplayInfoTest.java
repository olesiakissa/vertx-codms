package testing.model;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import system.model.ClientDisplayInfo;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Set;

import static org.junit.Assert.*;

public class ClientDisplayInfoTest {

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
        ClientDisplayInfo client = new ClientDisplayInfo(1, "name", "ZXCASD", "1234567890", "region", "dataplan");
        System.out.println(client.toString());
        String expected = new StringBuilder()
                .append("ClientDisplayInfo(id=").append(client.getId())
                .append(", fullName=").append(client.getFullName())
                .append(", passportCode=").append(client.getPassportCode())
                .append(", phoneNumber=").append(client.getPhoneNumber())
                .append(", regionName=").append(client.getRegionName())
                .append(", dataplanTitle=").append(client.getDataplanTitle())
                .append(")")
                .toString();
        String actual = outContent.toString().trim().replace("\r", "");
        assertEquals(expected, actual);
    }

    //region [SETTERS]
    @Test
    public void setId() throws NoSuchFieldException, IllegalAccessException {
        ClientDisplayInfo client = new ClientDisplayInfo();
        client.setId(22);
        Field field = client.getClass().getDeclaredField("id");
        field.setAccessible(true);
        Assert.assertEquals(22, field.get(client));
    }

    @Test
    public void setFullName() throws NoSuchFieldException, IllegalAccessException {
        ClientDisplayInfo client = new ClientDisplayInfo();
        client.setFullName("client");
        Field field = client.getClass().getDeclaredField("fullName");
        field.setAccessible(true);
        Assert.assertEquals("client", field.get(client));
    }

    @Test
    public void setPassportCode() throws NoSuchFieldException, IllegalAccessException {
        ClientDisplayInfo client = new ClientDisplayInfo();
        client.setPassportCode("ZXCASD");
        Field field = client.getClass().getDeclaredField("passportCode");
        field.setAccessible(true);
        Assert.assertEquals("ZXCASD", field.get(client));
    }

    @Test
    public void setPhoneNumber() throws NoSuchFieldException, IllegalAccessException {
        ClientDisplayInfo client = new ClientDisplayInfo();
        client.setPhoneNumber("1234567890");
        Field field = client.getClass().getDeclaredField("phoneNumber");
        field.setAccessible(true);
        Assert.assertEquals("1234567890", field.get(client));
    }

    @Test
    public void setRegionName() throws NoSuchFieldException, IllegalAccessException {
        ClientDisplayInfo client = new ClientDisplayInfo();
        client.setRegionName("reg");
        Field field = client.getClass().getDeclaredField("regionName");
        field.setAccessible(true);
        Assert.assertEquals("reg", field.get(client));
    }

    @Test
    public void setDataplanTitle() throws NoSuchFieldException, IllegalAccessException {
        ClientDisplayInfo client = new ClientDisplayInfo();
        client.setDataplanTitle("title");
        Field field = client.getClass().getDeclaredField("dataplanTitle");
        field.setAccessible(true);
        Assert.assertEquals("title", field.get(client));
    }
    //endregion

    //region [GETTERS]
    @Test
    public void getId() throws NoSuchFieldException, IllegalAccessException {
        ClientDisplayInfo client = new ClientDisplayInfo();
        Field field = client.getClass().getDeclaredField("id");
        field.setAccessible(true);
        field.set(client, 1331);
        int actual = client.getId();
        assertEquals(1331, actual);
    }

    @Test
    public void getFullName() throws NoSuchFieldException, IllegalAccessException {
        ClientDisplayInfo client = new ClientDisplayInfo();
        Field field = client.getClass().getDeclaredField("fullName");
        field.setAccessible(true);
        field.set(client, "XAVIER");
        String actual = client.getFullName();
        assertEquals("XAVIER", actual);
    }

    @Test
    public void getPassportCode() throws NoSuchFieldException, IllegalAccessException {
        ClientDisplayInfo client = new ClientDisplayInfo();
        Field field = client.getClass().getDeclaredField("passportCode");
        field.setAccessible(true);
        field.set(client, "SSS123");
        String actual = client.getPassportCode();
        assertEquals("SSS123", actual);
    }

    @Test
    public void getPhoneNumber() throws NoSuchFieldException, IllegalAccessException {
        ClientDisplayInfo client = new ClientDisplayInfo();
        Field field = client.getClass().getDeclaredField("phoneNumber");
        field.setAccessible(true);
        field.set(client, "1234567890");
        String actual = client.getPhoneNumber();
        assertEquals("1234567890", actual);
    }

    @Test
    public void getRegionName() throws NoSuchFieldException, IllegalAccessException {
        ClientDisplayInfo client = new ClientDisplayInfo();
        Field field = client.getClass().getDeclaredField("regionName");
        field.setAccessible(true);
        field.set(client, "Odessa region");
        String actual = client.getRegionName();
        assertEquals("Odessa region", actual);
    }

    @Test
    public void getDataplanTitle() throws NoSuchFieldException, IllegalAccessException {
        ClientDisplayInfo client = new ClientDisplayInfo();
        Field field = client.getClass().getDeclaredField("dataplanTitle");
        field.setAccessible(true);
        field.set(client, "Unlimited");
        String actual = client.getDataplanTitle();
        assertEquals("Unlimited", actual);
    }
    //endregion

    //region [CONSTRAINTS]
    @Test
    public void idIsInvalid() {
        ClientDisplayInfo client = new ClientDisplayInfo(-9, "name", "ZXCASD", "1234567890", "region", "dataplan");
        Set<ConstraintViolation<ClientDisplayInfo>> constraintViolationSet = validator.validate(client);
        assertEquals(2, constraintViolationSet.size());
    }

    @Test
    public void fullNameIsNull() {
        ClientDisplayInfo client = new ClientDisplayInfo(55, null, "ZXCASD", "1234567890", "region", "dataplan");
        Set<ConstraintViolation<ClientDisplayInfo>> constraintViolationSet = validator.validate(client);
        assertEquals(1, constraintViolationSet.size());
    }

    @Test
    public void fullNameIsEmpty() {
        ClientDisplayInfo client = new ClientDisplayInfo(6, "", "ZXCASD", "1234567890", "region", "dataplan");
        Set<ConstraintViolation<ClientDisplayInfo>> constraintViolationSet = validator.validate(client);
        assertEquals(1, constraintViolationSet.size());
    }


    @Test
    public void passportCodeIsNull() {
        ClientDisplayInfo client = new ClientDisplayInfo(66, "name", null, "1234567890", "region", "dataplan");
        Set<ConstraintViolation<ClientDisplayInfo>> constraintViolationSet = validator.validate(client);
        assertEquals(1, constraintViolationSet.size());
    }

    @Test
    public void passportCodeIsEmpty() {
        ClientDisplayInfo client = new ClientDisplayInfo(2, "name", "", "1234567890", "region", "dataplan");
        Set<ConstraintViolation<ClientDisplayInfo>> constraintViolationSet = validator.validate(client);
        assertEquals(1, constraintViolationSet.size());
    }

    @Test
    public void phoneNumberIsNull() {
        ClientDisplayInfo client = new ClientDisplayInfo(8, "name", "ZXCASD", null, "region", "dataplan");
        Set<ConstraintViolation<ClientDisplayInfo>> constraintViolationSet = validator.validate(client);
        assertEquals(1, constraintViolationSet.size());
    }

    @Test
    public void phoneNumberIsEmpty() {
        ClientDisplayInfo client = new ClientDisplayInfo(123, "name", "ZXCASD", "", "region", "dataplan");
        Set<ConstraintViolation<ClientDisplayInfo>> constraintViolationSet = validator.validate(client);
        assertEquals(1, constraintViolationSet.size());
    }

    @Test
    public void regionNameIsNull() {
        ClientDisplayInfo client = new ClientDisplayInfo(9, "name", "ZXCASD", "1234567890", null, "dataplan");
        Set<ConstraintViolation<ClientDisplayInfo>> constraintViolationSet = validator.validate(client);
        assertEquals(1, constraintViolationSet.size());
    }

    @Test
    public void regionNameIsEmpty() {
        ClientDisplayInfo client = new ClientDisplayInfo(77, "name", "ZXCASD", "1234567890", "", "dataplan");
        Set<ConstraintViolation<ClientDisplayInfo>> constraintViolationSet = validator.validate(client);
        assertEquals(1, constraintViolationSet.size());
        //endregion
    }

    @Test
    public void dataplanTitleIsNull() {
        ClientDisplayInfo client = new ClientDisplayInfo(2, "name", "ZXCASD", "1234567890", "region", null);
        Set<ConstraintViolation<ClientDisplayInfo>> constraintViolationSet = validator.validate(client);
        assertEquals(1, constraintViolationSet.size());
    }

    @Test
    public void dataplanTitleIsEmpty() {
        ClientDisplayInfo client = new ClientDisplayInfo(77, "name", "ZXCASD", "1234567890", "region", "");
        Set<ConstraintViolation<ClientDisplayInfo>> constraintViolationSet = validator.validate(client);
        assertEquals(1, constraintViolationSet.size());
        //endregion
    }
}