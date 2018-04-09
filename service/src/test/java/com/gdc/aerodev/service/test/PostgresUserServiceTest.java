package com.gdc.aerodev.service.test;

import com.gdc.aerodev.dao.postgres.PostgresUserDao;
import com.gdc.aerodev.model.User;
import com.gdc.aerodev.service.postgres.PostgresUserService;
import com.opentable.db.postgres.embedded.FlywayPreparer;
import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.PreparedDbRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class PostgresUserServiceTest {

    private String tableName = "user_test";
    private String userName = "Bob";
    private String userPassword = "p@ssw0rd";
    private String userEmail = "email";
    private short level = 100;

    @Rule
    public PreparedDbRule db = EmbeddedPostgresRules.preparedDatabase(FlywayPreparer.forClasspathLocation("user-service"));

    //Create User tests

    @Test
    public void testCreateUser(){
        PostgresUserService service = getService();
        PostgresUserDao dao = service.getDao();
        int size = dao.count();
        service.createUser(userName, userPassword, userEmail);
        assertNotNull(dao.getByName(userName));
        assertEquals(size + 1, dao.count());
    }

    @Test
    public void testCreateExistentUser(){
        PostgresUserService service = getService();
        PostgresUserDao dao = service.getDao();
        assertNotNull(service.createUser(userName, userPassword, userEmail));
        int size = dao.count();
        assertNull(service.createUser(userName, userPassword, userEmail));
        assertEquals(size, dao.count());
    }

    @Test
    public void testCreateEmptyName(){
        PostgresUserService service = getService();
        PostgresUserDao dao = service.getDao();
        int size = dao.count();
        assertNull(service.createUser("", userPassword, userEmail));
        assertEquals(size, dao.count());
    }

    @Test
    public void testCreateExistentEmail(){
        PostgresUserService service = getService();
        PostgresUserDao dao = service.getDao();
        assertNotNull(service.createUser(userName, userPassword, userEmail));
        int size = dao.count();
        assertNull(service.createUser("second", "new", userEmail));
        assertEquals(size, dao.count());
    }

    //Update User tests

    @Test
    public void testUpdateUser(){
        PostgresUserService service = getService();
        PostgresUserDao dao = service.getDao();
        User before = dao.getById(1L);
        assertNotNull(service.updateUser(1L, userName, userPassword, userEmail, level));
        assertNotEquals(before.getUserName(), dao.getById(1L).getUserName());
    }

    @Test
    public void testUpdateWithEmptyParams(){
        PostgresUserService service = getService();
        PostgresUserDao dao =service.getDao();
        Long id = service.createUser(userName, userPassword, userEmail);
        User before = dao.getById(id);
        assertNull(service.updateUser(id, "", "", "", (short) 0));
        User after = dao.getById(id);
        assertEquals(before.getUserName(), after.getUserName());
        assertEquals(before.getUserPassword(), after.getUserPassword());
        assertEquals(before.getUserEmail(), after.getUserEmail());
        assertEquals(before.getUserLevel(), after.getUserLevel());
    }

    private PostgresUserService getService(){
        return new PostgresUserService(db.getTestDatabase());
    }

}
