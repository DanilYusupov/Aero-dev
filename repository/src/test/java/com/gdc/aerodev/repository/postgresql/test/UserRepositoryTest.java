package com.gdc.aerodev.repository.postgresql.test;

import com.gdc.aerodev.model.User;
import com.gdc.aerodev.repository.postgresql.UserRepository;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.springframework.data.domain.PageRequest.*;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@AutoConfigureEmbeddedDatabase
@FlywayTest
@DataJpaTest
public class UserRepositoryTest {
//    @Autowired
//    private TestEntityManager entityManager;
    @Autowired
    private UserRepository repository;
    /**
     * Params below are taken from migration file.
     */
    private int usersCount = 5;
    private Long id = 1L;
    private String name = "Aigul";
    private String password = "passwrd";
    private String email = "eml";
    private short level = 68;
    private String country = "Russia";
    private String secondTopName = "Mark";
    private String thirdTopName = "Aliya";

    @Test
    public void findByIdTest(){
        User user = repository.findByUserId(id);
        assertEquals(name, user.getUserName());
        assertEquals(country, user.getUserCountry());
        assertFalse(user.isMale());
    }

    @Test
    public void findByNameTest(){
        User user = repository.findByUserName(name);
        assertEquals(password, user.getUserPassword());
        assertEquals(email, user.getUserEmail());
        assertEquals(level, user.getUserLevel());
    }

    @Test
    public void findTopThreeTest(){
        List<User> users = repository.ratingOrdered(of(0, 3));
        assertEquals(3, users.size());
        assertEquals(secondTopName, users.get(1).getUserName());
        assertEquals(thirdTopName, users.get(2).getUserName());
    }

    @Test
    public void findAllTest(){
        List<User> users = repository.findAll();
        assertEquals(usersCount, users.size());
    }

    @Test
    public void createTest(){
        name = "Anton";
        User newOne = new User(
                name,
                password,
                "e",
                true
        );
        newOne = repository.save(newOne);
        assertEquals(name, repository.findByUserId(newOne.getUserId()).getUserName());
    }

    @Test
    public void updateTest(){
        String newName = "Broccoli";
        User user = repository.findByUserName(name);
        user.setUserName(newName);
        user = repository.save(user);
        assertEquals(id, user.getUserId());
        assertEquals(newName, repository.findByUserId(id).getUserName());
    }

    @Test
    public void deleteTest(){
        name = "New";
        User newOne = new User(
                name,
                password,
                "e",
                true
        );
        newOne = repository.save(newOne);
        assertEquals(usersCount + 1, repository.findAll().size());
        repository.deleteById(newOne.getUserId());
        assertEquals(usersCount, repository.findAll().size());
    }

    //Abnormal tests

    @Test
    public void getByFakeIdTest(){
        assertNull(repository.findByUserId(0L));
        assertNull(repository.findByUserId(null));
    }

    @Test
    public void getByFakeName(){
        assertNull(repository.findByUserName(""));
        assertNull(repository.findByUserName(null));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveExistentName(){
        User user = new User(
                name,
                password,
                "e",
                true
        );
        repository.save(user);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void deleteNonExistentUserTest(){
        repository.deleteById(0L);
    }
}
