package testing.model;

import org.junit.BeforeClass;
import org.junit.Test;
import system.model.RegionBiggestCity;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Set;

import static org.junit.Assert.*;

public class RegionBiggestCityTest {

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
        RegionBiggestCity regionBiggestCity = new RegionBiggestCity(1, "biggestCity", 2333332);
        System.out.println(regionBiggestCity.toString());
        String expected = new StringBuilder()
                .append("RegionBiggestCity(regionID=").append(regionBiggestCity.getRegionID())
                .append(", name=").append(regionBiggestCity.getName())
                .append(", population=").append(regionBiggestCity.getPopulation())
                .append(")")
                .toString();
        String actual = outContent.toString().trim().replace("\r", "");
        assertEquals(expected, actual);
    }

    //region [SETTERS]
    @Test
    public void setRegionID() throws NoSuchFieldException, IllegalAccessException {
        RegionBiggestCity regionBiggestCity = new RegionBiggestCity();
        regionBiggestCity.setRegionID(1);
        Field field = regionBiggestCity.getClass().getDeclaredField("regionID");
        field.setAccessible(true);
        assertEquals(1, field.get(regionBiggestCity));
    }

    @Test
    public void setName() throws NoSuchFieldException, IllegalAccessException {
        RegionBiggestCity regionBiggestCity = new RegionBiggestCity();
        regionBiggestCity.setName("regionBiggestCity");
        Field field = regionBiggestCity.getClass().getDeclaredField("name");
        field.setAccessible(true);
        assertEquals("regionBiggestCity", field.get(regionBiggestCity));
    }

    @Test
    public void setPopulation() throws NoSuchFieldException, IllegalAccessException {
        RegionBiggestCity regionBiggestCity = new RegionBiggestCity();
        regionBiggestCity.setPopulation(23442341L);
        Field field = regionBiggestCity.getClass().getDeclaredField("population");
        field.setAccessible(true);
        assertEquals(23442341L, field.get(regionBiggestCity));
    }
    //endregion

    //region [GETTERS]
    @Test
    public void getRegionID() throws NoSuchFieldException, IllegalAccessException {
        RegionBiggestCity regionBiggestCity = new RegionBiggestCity();
        Field field = regionBiggestCity.getClass().getDeclaredField("regionID");
        field.setAccessible(true);
        field.set(regionBiggestCity, 12);
        int actual = regionBiggestCity.getRegionID();
        assertEquals(12, actual);
    }

    @Test
    public void getName() throws NoSuchFieldException, IllegalAccessException {
        RegionBiggestCity regionBiggestCity = new RegionBiggestCity();
        Field field = regionBiggestCity.getClass().getDeclaredField("name");
        field.setAccessible(true);
        field.set(regionBiggestCity, "regionBiggestCity");
        String actual = regionBiggestCity.getName();
        assertEquals("regionBiggestCity", actual);
    }

    @Test
    public void getPopulation() throws NoSuchFieldException, IllegalAccessException {
        RegionBiggestCity regionBiggestCity = new RegionBiggestCity();
        Field field = regionBiggestCity.getClass().getDeclaredField("population");
        field.setAccessible(true);
        field.set(regionBiggestCity, 1234234234L);
        long actual = regionBiggestCity.getPopulation();
        assertEquals(1234234234L, actual);
    }
    //endregion

    //region [CONSTRAINTS]
    @Test
    public void regionIDIsInvalid() {
        RegionBiggestCity regionBiggestCity = new RegionBiggestCity(-34443, "name", 22233);
        Set<ConstraintViolation<RegionBiggestCity>> constraintViolationSet = validator.validate(regionBiggestCity);
        assertEquals(2, constraintViolationSet.size());
    }

    @Test
    public void nameIsNull() {
        RegionBiggestCity regionBiggestCity = new RegionBiggestCity(1, null, 22233);
        Set<ConstraintViolation<RegionBiggestCity>> constraintViolationSet = validator.validate(regionBiggestCity);
        assertEquals(1, constraintViolationSet.size());
    }

    @Test
    public void nameIsEmpty() {
        RegionBiggestCity regionBiggestCity = new RegionBiggestCity(1, "", 22233);
        Set<ConstraintViolation<RegionBiggestCity>> constraintViolationSet = validator.validate(regionBiggestCity);
        assertEquals(1, constraintViolationSet.size());
    }

    @Test
    public void populationIsInvalid() {
        RegionBiggestCity regionBiggestCity = new RegionBiggestCity(11, "name", -2);
        Set<ConstraintViolation<RegionBiggestCity>> constraintViolationSet = validator.validate(regionBiggestCity);
        assertEquals(2, constraintViolationSet.size());
    }
    //endregion
}