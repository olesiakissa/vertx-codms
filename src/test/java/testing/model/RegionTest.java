package testing.model;

import org.junit.BeforeClass;
import org.junit.Test;
import system.model.Region;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Set;

import static org.junit.Assert.*;

public class RegionTest {

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
        Region region = new Region(1, null, 22233);
        System.out.println(region.toString());
        String expected = new StringBuilder()
                .append("Region(regionID=").append(region.getRegionID())
                .append(", name=").append(region.getName())
                .append(", population=").append(region.getPopulation())
                .append(")")
                .toString();
        String actual = outContent.toString().trim().replace("\r", "");
        assertEquals(expected, actual);
    }

    //region [SETTERS]
    @Test
    public void setRegionID() throws NoSuchFieldException, IllegalAccessException {
        Region region = new Region();
        region.setRegionID(1);
        Field field = region.getClass().getDeclaredField("regionID");
        field.setAccessible(true);
        assertEquals(1, field.get(region));
    }

    @Test
    public void setName() throws NoSuchFieldException, IllegalAccessException {
        Region region = new Region();
        region.setName("region");
        Field field = region.getClass().getDeclaredField("name");
        field.setAccessible(true);
        assertEquals("region", field.get(region));
    }

    @Test
    public void setPopulation() throws NoSuchFieldException, IllegalAccessException {
        Region region = new Region();
        region.setPopulation(14441266786L);
        Field field = region.getClass().getDeclaredField("population");
        field.setAccessible(true);
        assertEquals(14441266786L, field.get(region));
    }
    //endregion

    //region [GETTERS]
    @Test
    public void getRegionID() throws NoSuchFieldException, IllegalAccessException {
        Region region = new Region();
        Field field = region.getClass().getDeclaredField("regionID");
        field.setAccessible(true);
        field.set(region, 23);
        int actual = region.getRegionID();
        assertEquals(23, actual);
    }

    @Test
    public void getName() throws NoSuchFieldException, IllegalAccessException {
        Region region = new Region();
        Field field = region.getClass().getDeclaredField("name");
        field.setAccessible(true);
        field.set(region, "name");
        String actual = region.getName();
        assertEquals("name", actual);
    }

    @Test
    public void getPopulation() throws NoSuchFieldException, IllegalAccessException {
        Region region = new Region();
        Field field = region.getClass().getDeclaredField("population");
        field.setAccessible(true);
        field.set(region, 14441266786L);
        long actual = region.getPopulation();
        assertEquals(14441266786L, actual);
    }
    //endregion

    //region [CONSTRAINTS]
    @Test
    public void regionIDIsInvalid() {
        Region region = new Region(-34443, "name", 22233);
        Set<ConstraintViolation<Region>> constraintViolationSet = validator.validate(region);
        assertEquals(2, constraintViolationSet.size());
    }

    @Test
    public void nameIsNull() {
        Region region = new Region(1, null, 22233);
        Set<ConstraintViolation<Region>> constraintViolationSet = validator.validate(region);
        assertEquals(1, constraintViolationSet.size());
    }

    @Test
    public void nameIsEmpty() {
        Region region = new Region(1, "", 22233);
        Set<ConstraintViolation<Region>> constraintViolationSet = validator.validate(region);
        assertEquals(1, constraintViolationSet.size());
    }

    @Test
    public void populationIsInvalid() {
        Region region = new Region(11, "name", -2);
        Set<ConstraintViolation<Region>> constraintViolationSet = validator.validate(region);
        assertEquals(2, constraintViolationSet.size());
    }
    //endregion
}