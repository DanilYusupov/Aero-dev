package com.gdc.aerodev.dao.test;

import com.gdc.aerodev.dao.UserDao;
import com.gdc.aerodev.model.User;
import com.opentable.db.postgres.embedded.FlywayPreparer;
import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.PreparedDbRule;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserDaoTest {

    private String tableName = "user_test";

    @Rule
    public PreparedDbRule db = EmbeddedPostgresRules.preparedDatabase(FlywayPreparer.forClasspathLocation("db"));

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
        String name = "Novichok";
        String password = "p@ssw0rd";
        String email = "email";
        User user = new User(name, password, email);
        Long id = dao.save(user);
        assertEquals(name, dao.getById(id).getUserName());
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
        assertEquals(name, dao.getById(id).getUserName());
        assertEquals(level, dao.getById(id).getUserLevel());
    }

    private UserDao getDao() {
        return new UserDao(new JdbcTemplate(db.getTestDatabase()), tableName);
    }

}
