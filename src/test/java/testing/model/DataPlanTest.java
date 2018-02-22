package testing.model;

import org.junit.BeforeClass;
import org.junit.Test;
import system.model.DataPlan;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Set;

import static org.junit.Assert.*;

public class DataPlanTest {

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
        DataPlan dataPlan = new DataPlan(1, "data plan title", "simple description");
        System.out.println(dataPlan.toString());
        String expected = new StringBuilder()
                .append("DataPlan(dataplanID=").append(dataPlan.getDataplanID())
                .append(", title=").append(dataPlan.getTitle())
                .append(", description=").append(dataPlan.getDescription())
                .append(")")
                .toString();
        String actual = outContent.toString().trim().replace("\r", "");
        assertEquals(expected, actual);
    }

    //region GETTERS & SETTERS
    @Test
    public void setDataplanID() throws NoSuchFieldException, IllegalAccessException {
        DataPlan dataPlan = new DataPlan();
        dataPlan.setDataplanID(1);
        Field field = dataPlan.getClass().getDeclaredField("dataplanID");
        field.setAccessible(true);
        assertEquals(1, field.get(dataPlan));
    }

    @Test
    public void setTitle() throws NoSuchFieldException, IllegalAccessException {
        DataPlan dataPlan = new DataPlan();
        dataPlan.setTitle("data plan title");
        Field field = dataPlan.getClass().getDeclaredField("title");
        field.setAccessible(true);
        assertEquals("data plan title", field.get(dataPlan));
    }

    @Test
    public void setDescription() throws NoSuchFieldException, IllegalAccessException {
        DataPlan dataPlan = new DataPlan();
        dataPlan.setDescription("simple description");
        Field field = dataPlan.getClass().getDeclaredField("description");
        field.setAccessible(true);
        assertEquals("simple description", field.get(dataPlan));
    }

    @Test
    public void getDataplanID() throws NoSuchFieldException, IllegalAccessException {
        DataPlan dataPlan = new DataPlan();
        Field field = dataPlan.getClass().getDeclaredField("dataplanID");
        field.setAccessible(true);
        field.set(dataPlan, 12);
        int actual = dataPlan.getDataplanID();
        assertEquals(12, actual);
    }

    @Test
    public void getTitle() throws NoSuchFieldException, IllegalAccessException {
        DataPlan dataPlan = new DataPlan();
        Field field = dataPlan.getClass().getDeclaredField("title");
        field.setAccessible(true);
        field.set(dataPlan, "tttt");
        String actual = dataPlan.getTitle();
        assertEquals("tttt", actual);
    }

    @Test
    public void getDescription() throws NoSuchFieldException, IllegalAccessException {
        DataPlan dataPlan = new DataPlan();
        Field field = dataPlan.getClass().getDeclaredField("description");
        field.setAccessible(true);
        field.set(dataPlan, "DDD");
        String actual = dataPlan.getDescription();
        assertEquals("DDD", actual);
    }
    //endregion

    //region CONSTRAINTS
    @Test
    public void dataplanIDIsInvalid() {
        DataPlan dataPlan = new DataPlan(-123, "data plan title", "simple description");
        Set<ConstraintViolation<DataPlan>> constraintViolationSet = validator.validate(dataPlan);
        assertEquals(2, constraintViolationSet.size());
    }

    @Test
    public void titleIsNull() {
        DataPlan dataPlan = new DataPlan(1, null, "simple description");
        Set<ConstraintViolation<DataPlan>> constraintViolationSet = validator.validate(dataPlan);
        assertEquals(1, constraintViolationSet.size());
    }

    @Test
    public void titleIsEmpty() {
        DataPlan dataPlan = new DataPlan(1, "", "simple description");
        Set<ConstraintViolation<DataPlan>> constraintViolationSet = validator.validate(dataPlan);
        assertEquals(1, constraintViolationSet.size());
    }

    @Test
    public void descriptionIsNull() {
        DataPlan dataPlan = new DataPlan(1, "data plan title", null);
        Set<ConstraintViolation<DataPlan>> constraintViolationSet = validator.validate(dataPlan);
        assertEquals(1, constraintViolationSet.size());
    }

    @Test
    public void descriptionIsEmpty() {
        DataPlan dataPlan = new DataPlan(1, "data plan title", "");
        Set<ConstraintViolation<DataPlan>> constraintViolationSet = validator.validate(dataPlan);
        assertEquals(1, constraintViolationSet.size());
    }
    //endregion
}