package testing.model;

import org.junit.BeforeClass;
import org.junit.Test;
import system.model.DataPlanColor;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Set;

import static org.junit.Assert.*;

public class DataPlanColorTest {

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
        DataPlanColor dataPlanColor = new DataPlanColor(2, "#fefefe");
        System.out.println(dataPlanColor.toString());
        String expected = new StringBuilder()
                .append("DataPlanColor(dataplanID=").append(dataPlanColor.getDataplanID())
                .append(", colorCode=").append(dataPlanColor.getColorCode())
                .append(")")
                .toString();
        String actual = outContent.toString().trim().replace("\r", "");
        assertEquals(expected, actual);
    }

    //region [SETTERS]
    @Test
    public void setDataplanID() throws NoSuchFieldException, IllegalAccessException {
        DataPlanColor dataPlanColor = new DataPlanColor();
        dataPlanColor.setDataplanID(1);
        Field field = dataPlanColor.getClass().getDeclaredField("dataplanID");
        field.setAccessible(true);
        assertEquals(1, field.get(dataPlanColor));
    }

    @Test
    public void setColorCode() throws NoSuchFieldException, IllegalAccessException {
        DataPlanColor dataPlanColor = new DataPlanColor();
        dataPlanColor.setColorCode("#fefefe");
        Field field = dataPlanColor.getClass().getDeclaredField("colorCode");
        field.setAccessible(true);
        assertEquals("#fefefe", field.get(dataPlanColor));
    }
    //endregion

    //region [GETTERS]
    @Test
    public void getDataplanID() throws NoSuchFieldException, IllegalAccessException {
        DataPlanColor dataPlanColor = new DataPlanColor();
        Field field = dataPlanColor.getClass().getDeclaredField("dataplanID");
        field.setAccessible(true);
        field.set(dataPlanColor, 4);
        int actual = dataPlanColor.getDataplanID();
        assertEquals(4, actual);
    }

    @Test
    public void getColorCode() throws NoSuchFieldException, IllegalAccessException {
        DataPlanColor dataPlanColor = new DataPlanColor();
        Field field = dataPlanColor.getClass().getDeclaredField("colorCode");
        field.setAccessible(true);
        field.set(dataPlanColor, "#fefefe");
        String actual = dataPlanColor.getColorCode();
        assertEquals( "#fefefe", actual);
    }
    //endregion

    //region [CONSTRAINTS]
    @Test
    public void dataplanIDIsInvalid() {
        DataPlanColor dataPlanColor = new DataPlanColor(-222, "#fefefe");
        Set<ConstraintViolation<DataPlanColor>> constraintViolationSet = validator.validate(dataPlanColor);
        assertEquals(2,  constraintViolationSet.size());
    }

    /**
     * For pattern see {@link DataPlanColor#COLOR_CODE_PATTERN}
     */
    @Test
    public void colorCodeIsInvalid() {
        DataPlanColor dataPlanColor = new DataPlanColor(3, "qwerty123");
        Set<ConstraintViolation<DataPlanColor>> constraintViolationSet = validator.validate(dataPlanColor);
        assertEquals(1,  constraintViolationSet.size());
    }

    @Test
    public void colorCodeIsNull() {
        DataPlanColor dataPlanColor = new DataPlanColor(3, null);
        Set<ConstraintViolation<DataPlanColor>> constraintViolationSet = validator.validate(dataPlanColor);
        assertEquals(1,  constraintViolationSet.size());
    }

    @Test
    public void colorCodeIsEmpty() {
        DataPlanColor dataPlanColor = new DataPlanColor(3, "");
        Set<ConstraintViolation<DataPlanColor>> constraintViolationSet = validator.validate(dataPlanColor);
        assertEquals(2,  constraintViolationSet.size());
    }
    //endregion
}