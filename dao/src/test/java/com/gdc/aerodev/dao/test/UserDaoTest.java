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
    public void testGetById(){
        UserDao dao = new UserDao(new JdbcTemplate(db.getTestDatabase()), tableName);
        User user = dao.getById(1L);
        assertEquals("Petr", user.getUserName());
        assertEquals("velikii@spb.ru", user.getUserEmail());
        assertEquals(0, user.getUserLevel());
    }
    @Test
    public void testGetByName(){
        UserDao dao = new UserDao(new JdbcTemplate(db.getTestDatabase()), tableName);
        User user = dao.getByName("Petr");
        assertEquals("velikii@spb.ru", user.getUserEmail());
        assertEquals(0, user.getUserLevel());
    }

    @Test
    public void testGetAll(){
        UserDao dao = new UserDao(new JdbcTemplate(db.getTestDatabase()), tableName);
        List<User> list = dao.getAll();
        assertEquals(3, list.size());
    }

}
