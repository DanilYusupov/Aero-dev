package com.gdc.aerodev.dao.test.postgres;

import com.gdc.aerodev.dao.UserDao;
import com.gdc.aerodev.dao.postgres.PostgresUserDao;
import com.gdc.aerodev.dao.exception.DaoException;
import com.gdc.aerodev.model.User;
import com.opentable.db.postgres.embedded.FlywayPreparer;
import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.PreparedDbRule;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.*;

public class PostgresUserDaoTest {

    private String tableName = "user_test";
    private String name = "Novichok";
    private String password = "p@ssw0rd";
    private String email = "email";
    private User user = new User(name, password, email, true);

    @Rule
    public PreparedDbRule db = EmbeddedPostgresRules.preparedDatabase(FlywayPreparer.forClasspathLocation("user"));

    //Standard tests

    @Test
    public void testGetById() {
        UserDao dao = getDao();
        User user = dao.getById(1L);
        assertEquals("Petr", user.getUserName());
        assertEquals("velikii@spb.ru", user.getUserEmail());
        assertEquals(0, user.getUserLevel());
    }

    @Test
    public void testGetByName() {
        UserDao dao = getDao();
        User user = dao.getByName("Petr");
        assertEquals("velikii@spb.ru", user.getUserEmail());
        assertEquals(0, user.getUserLevel());
    }

    @Test
    public void testGetAll() {
        UserDao dao = getDao();
        List<User> list = dao.getAll();
        assertEquals(3, list.size());
    }

    @Test
    public void testInsert() {
        UserDao dao = getDao();
        Long id = dao.save(user);
        User user = dao.getById(id);;
        assertEquals(name, user.getUserName());
        assertNotNull(user.getUserCountry());
        assertNotNull(user.getUserCity());
        assertNotNull(user.getUserFirstName());
        assertNotNull(user.getUserLastName());
    }

    @Test
    public void testUpdate() {
        UserDao dao = getDao();
        Long id = 1L;
        String name = "Denis";
        short level = 1;
        User user = dao.getById(id);
        assertEquals("Petr", user.getUserName());
        user.setUserName(name);
        user.setUserLevel(level);
        dao.save(user);
        User newUser = dao.getById(id);
        assertEquals(name, newUser.getUserName());
        assertEquals(level, newUser.getUserLevel());
        assertNotNull(newUser.getUserCountry());
        assertNotNull(newUser.getUserCity());
        assertNotNull(newUser.getUserFirstName());
        assertNotNull(newUser.getUserLastName());
    }

    @Test
    public void testDelete() {
        UserDao dao = getDao();
        int size = dao.getAll().size();
        dao.delete(2L);
        assertEquals(size - 1, dao.getAll().size());
    }

    @Test
    public void testExistentEmail(){
        UserDao dao = getDao();
        assertEquals("Petr", dao.existentEmail("velikii@spb.ru"));
    }

    @Test
    public void testNonExistentEmail(){
        UserDao dao = getDao();
        assertNull(dao.existentEmail("!!!"));
    }

    @Test
    public void testCount(){
        UserDao dao = getDao();
        assertEquals(3, dao.count());
    }

    //Abnormal tests

    @Test
    public void testGetByIdNonExistent() {
        UserDao dao = getDao();
        assertNull(dao.getById(-1L));
    }

    @Test
    public void testGetByNameNonExistent() {
        UserDao dao = getDao();
        assertNull(dao.getByName("!!!"));
    }

    @Test(expected = DaoException.class)
    public void testInsertExistentUserException() {
        UserDao dao = getDao();
        String newName = "Petr";
        user.setUserName(newName);
        assertNull(dao.save(user));
    }

    @Test
    public void testInsertExistentUserDbSize() {
        UserDao dao = getDao();
        String newName = "Petr";
        user.setUserName(newName);
        int size = dao.getAll().size();
        try {
            assertNull(dao.save(user));
        } catch (DaoException e) {
            assertEquals(size, dao.getAll().size());
        }
    }

    @Test
    public void testDeleteNonExistentUser(){
        UserDao dao = getDao();
        assertFalse(dao.delete(-1L));
    }

    private UserDao getDao() {
        return new PostgresUserDao(new JdbcTemplate(db.getTestDatabase()), tableName);
    }

}
