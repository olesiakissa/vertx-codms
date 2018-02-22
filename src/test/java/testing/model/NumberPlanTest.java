package testing.model;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import system.model.NumberPlan;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Set;

import static org.junit.Assert.*;

public class NumberPlanTest {

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
        NumberPlan numberPlan = new NumberPlan(1, "1234567890");
        System.out.println(numberPlan.toString());
        String expected = new StringBuilder()
                .append("NumberPlan(dataplanID=").append(numberPlan.getDataplanID())
                .append(", phoneNumber=").append(numberPlan.getPhoneNumber())
                .append(")")
                .toString();
        String actual = outContent.toString().trim().replace("\r", "");
        assertEquals(expected, actual);
    }

    //region [SETTERS]
    @Test
    public void setDataplanID() throws NoSuchFieldException, IllegalAccessException {
        NumberPlan numberPlan = new NumberPlan();
        numberPlan.setDataplanID(1);
        Field field = numberPlan.getClass().getDeclaredField("dataplanID");
        field.setAccessible(true);
        assertEquals(1, field.get(numberPlan));
    }

    @Test
    public void setPhoneNumber() throws NoSuchFieldException, IllegalAccessException {
        NumberPlan numberPlan = new NumberPlan();
        numberPlan.setPhoneNumber("1234567890");
        Field field = numberPlan.getClass().getDeclaredField("phoneNumber");
        field.setAccessible(true);
        assertEquals("1234567890", field.get(numberPlan));
    }
    //endregion

    //region [SETTERS]
    @Test
    public void getDataplanID() throws NoSuchFieldException, IllegalAccessException {
        NumberPlan numberPlan = new NumberPlan();
        Field field = numberPlan.getClass().getDeclaredField("dataplanID");
        field.setAccessible(true);
        field.set(numberPlan, 4);
        int actual = numberPlan.getDataplanID();
        assertEquals(4, actual);
    }

    @Test
    public void getPhoneNumber() throws NoSuchFieldException, IllegalAccessException {
        NumberPlan numberPlan = new NumberPlan();
        Field field = numberPlan.getClass().getDeclaredField("phoneNumber");
        field.setAccessible(true);
        field.set(numberPlan, "1234567890");
        String actual = numberPlan.getPhoneNumber();
        assertEquals("1234567890", actual);
    }
    //endregion

    //region [CONSTRAINTS]
    @Test
    public void dataplanIDIsInvalid() {
        NumberPlan numberPlan = new NumberPlan(-8, "1234567890");
        Set<ConstraintViolation<NumberPlan>> constraintViolationSet = validator.validate(numberPlan);
        assertEquals(2, constraintViolationSet.size());
    }

    @Test
    public void phoneNumberIsNull() {
        NumberPlan numberPlan = new NumberPlan(1, null);
        Set<ConstraintViolation<NumberPlan>> constraintViolationSet = validator.validate(numberPlan);
        assertEquals(1, constraintViolationSet.size());
    }

    @Test
    public void phoneNumberIsEmpty() {
        NumberPlan numberPlan = new NumberPlan(1, "");
        Set<ConstraintViolation<NumberPlan>> constraintViolationSet = validator.validate(numberPlan);
        assertEquals(1, constraintViolationSet.size());
    }
    //endregion
}