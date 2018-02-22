package testing.model;

import org.junit.Test;
import system.model.Admin;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class AdminTest {

    @Test
    public void testToString() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Admin admin = new Admin(1,"admin", "admin", "MAIN_ADMIN");
        System.out.println(admin.toString());
        String expected = new StringBuilder()
                .append("Admin(id=").append(admin.getId())
                .append(", name=").append(admin.getName())
                .append(", password=").append(admin.getPassword())
                .append(", adminCredentials=").append(admin.getAdminCredentials())
                .append(")")
                .toString();
        String actual = outContent.toString().trim().replace("\r", "");
        assertEquals(expected, actual);
    }

    //region [SETTERS]
    @Test
    public void setId() throws NoSuchFieldException, IllegalAccessException {
        Admin admin = new Admin();
        admin.setId(1);
        Field field = admin.getClass().getDeclaredField("id");
        field.setAccessible(true);
        assertEquals(1, field.get(admin));
    }

    @Test
    public void setName() throws NoSuchFieldException, IllegalAccessException {
        Admin admin = new Admin();
        admin.setName("admin");
        Field field = admin.getClass().getDeclaredField("name");
        field.setAccessible(true);
        assertEquals("admin", field.get(admin));
    }

    @Test
    public void setPassword() throws NoSuchFieldException, IllegalAccessException {
        Admin admin = new Admin();
        admin.setPassword("password");
        Field field = admin.getClass().getDeclaredField("password");
        field.setAccessible(true);
        assertEquals("password", field.get(admin));
    }

    @Test
    public void setAdminCredentials() throws NoSuchFieldException, IllegalAccessException {
        Admin admin = new Admin();
        admin.setAdminCredentials("TEST_CREDENTIALS");
        Field field = admin.getClass().getDeclaredField("adminCredentials");
        field.setAccessible(true);
        assertEquals("TEST_CREDENTIALS", field.get(admin));
    }
    //endregion

    //region [GETTERS]
    @Test
    public void getId() throws NoSuchFieldException, IllegalAccessException {
        Admin admin = new Admin();
        Field field = admin.getClass().getDeclaredField("id");
        field.setAccessible(true);
        field.set(admin, 1);
        int actual = admin.getId();
        assertEquals(1, actual);
    }

    @Test
    public void getName() throws NoSuchFieldException, IllegalAccessException {
        Admin admin = new Admin();
        Field field = admin.getClass().getDeclaredField("name");
        field.setAccessible(true);
        field.set(admin, "admin");
        String actual = admin.getName();
        assertEquals("admin", actual);
    }

    @Test
    public void getPassword() throws NoSuchFieldException, IllegalAccessException {
        Admin admin = new Admin();
        Field field = admin.getClass().getDeclaredField("password");
        field.setAccessible(true);
        field.set(admin, "password");
        String actual = admin.getPassword();
        assertEquals("password", actual);
    }

    @Test
    public void getAdminCredentials() throws NoSuchFieldException, IllegalAccessException {
        Admin admin = new Admin();
        Field field = admin.getClass().getDeclaredField("adminCredentials");
        field.setAccessible(true);
        field.set(admin, "TEST_CREDENTIALS");
        String actual = admin.getAdminCredentials();
        assertEquals("TEST_CREDENTIALS", actual);
    }
    //endregion
}