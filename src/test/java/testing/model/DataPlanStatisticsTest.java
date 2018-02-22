package testing.model;

import org.junit.BeforeClass;
import org.junit.Test;
import system.model.DataPlanStatistics;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Set;

import static org.junit.Assert.*;

public class DataPlanStatisticsTest {

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
        DataPlanStatistics dataPlanStatistics = new DataPlanStatistics(1, "region", 3);
        System.out.println(dataPlanStatistics.toString());
        String expected = new StringBuilder()
                .append("DataPlanStatistics(regionID=").append(dataPlanStatistics.getRegionID())
                .append(", regionName=").append(dataPlanStatistics.getRegionName())
                .append(", dataplanID=").append(dataPlanStatistics.getDataplanID())
                .append(")")
                .toString();
        String actual = outContent.toString().trim().replace("\r", "");
        assertEquals(expected, actual);
    }

    //region GETTERS & SETTERS
    @Test
    public void setRegionID() throws NoSuchFieldException, IllegalAccessException {
        DataPlanStatistics dataPlanStatistics = new DataPlanStatistics();
        dataPlanStatistics.setRegionID(1);
        Field field = dataPlanStatistics.getClass().getDeclaredField("regionID");
        field.setAccessible(true);
        assertEquals(1, field.get(dataPlanStatistics));
    }

    @Test
    public void setRegionName() throws NoSuchFieldException, IllegalAccessException {
        DataPlanStatistics dataPlanStatistics = new DataPlanStatistics();
        dataPlanStatistics.setRegionName("region");
        Field field = dataPlanStatistics.getClass().getDeclaredField("regionName");
        field.setAccessible(true);
        assertEquals("region", field.get(dataPlanStatistics));
    }

    @Test
    public void setDataplanID() throws NoSuchFieldException, IllegalAccessException {
        DataPlanStatistics dataPlanStatistics = new DataPlanStatistics();
        dataPlanStatistics.setDataplanID(3);
        Field field = dataPlanStatistics.getClass().getDeclaredField("dataplanID");
        field.setAccessible(true);
        assertEquals(3, field.get(dataPlanStatistics));
    }

    @Test
    public void getRegionID() throws NoSuchFieldException, IllegalAccessException {
        DataPlanStatistics dataPlanStatistics = new DataPlanStatistics();
        Field field = dataPlanStatistics.getClass().getDeclaredField("regionID");
        field.setAccessible(true);
        field.set(dataPlanStatistics, 22);
        int actual = dataPlanStatistics.getRegionID();
        assertEquals(22, actual);
    }

    @Test
    public void getRegionName() throws NoSuchFieldException, IllegalAccessException {
        DataPlanStatistics dataPlanStatistics = new DataPlanStatistics();
        Field field = dataPlanStatistics.getClass().getDeclaredField("regionName");
        field.setAccessible(true);
        field.set(dataPlanStatistics, "region");
        String actual = dataPlanStatistics.getRegionName();
        assertEquals("region", actual);
    }

    @Test
    public void getDataplanID() throws NoSuchFieldException, IllegalAccessException {
        DataPlanStatistics dataPlanStatistics = new DataPlanStatistics();
        Field field = dataPlanStatistics.getClass().getDeclaredField("dataplanID");
        field.setAccessible(true);
        field.set(dataPlanStatistics, 4);
        int actual = dataPlanStatistics.getDataplanID();
        assertEquals(4, actual);
    }
    //endregion

    //region CONSTRAINTS
    @Test
    public void regionIDIsInvalid() {
        DataPlanStatistics dataPlanStatistics = new DataPlanStatistics(-34, "region", 1);
        Set<ConstraintViolation<DataPlanStatistics>> constraintViolationSet = validator.validate(dataPlanStatistics);
        assertEquals(2, constraintViolationSet.size());
    }

    @Test
    public void regionNameIsNull() {
        DataPlanStatistics dataPlanStatistics = new DataPlanStatistics(7, null, 1);
        Set<ConstraintViolation<DataPlanStatistics>> constraintViolationSet = validator.validate(dataPlanStatistics);
        assertEquals(1,  constraintViolationSet.size());
    }

    @Test
    public void regionNameIsEmpty() {
        DataPlanStatistics dataPlanStatistics = new DataPlanStatistics(7, "", 1);
        Set<ConstraintViolation<DataPlanStatistics>> constraintViolationSet = validator.validate(dataPlanStatistics);
        assertEquals(1,  constraintViolationSet.size());
    }

    @Test
    public void dataplanIDIsInvalid() {
        DataPlanStatistics dataPlanStatistics = new DataPlanStatistics(3, "region", -1);
        Set<ConstraintViolation<DataPlanStatistics>> constraintViolationSet = validator.validate(dataPlanStatistics);
        assertEquals(2, constraintViolationSet.size());
    }

    @Test
    public void percentOfCitizensIsInvalid() {
        DataPlanStatistics dataPlanStatistics = new DataPlanStatistics(3, "region", 1);
        Set<ConstraintViolation<DataPlanStatistics>> constraintViolationSet = validator.validate(dataPlanStatistics);
        assertEquals(1, constraintViolationSet.size());
    }
    //endregion
}