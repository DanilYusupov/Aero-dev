import com.gdc.aerodev.dao.specific.UserDao;
import com.gdc.aerodev.service.specific.UserService;
import com.opentable.db.postgres.embedded.FlywayPreparer;
import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.PreparedDbRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class UserServiceTest {

    private String tableName = "user_test";
    private String userName = "Bob";
    private String userPassword = "p@ssw0rd";
    private String userEmail = "email";

    @Rule
    public PreparedDbRule db = EmbeddedPostgresRules.preparedDatabase(FlywayPreparer.forClasspathLocation("user-service"));

    @Test
    public void createUserTest(){
        UserService service = getService();
        UserDao dao = service.getDao();
        int size = dao.getAll().size();
        service.createUser(userName, userPassword, userEmail);
        assertNotNull(dao.getByName(userName));
        assertEquals(size + 1, dao.getAll().size());
    }

    @Test
    public void createExistentUser(){
        UserService service = getService();
        UserDao dao = service.getDao();
        assertNotNull(service.createUser(userName, userPassword, userEmail));
        int size = dao.getAll().size();
        assertNull(service.createUser(userName, userPassword, userEmail));
        assertEquals(size, dao.getAll().size());
    }

    @Test
    public void createEmptyName(){
        UserService service = getService();
        UserDao dao = service.getDao();
        int size = dao.getAll().size();
        assertNull(service.createUser("", userPassword, userEmail));
        assertEquals(size, dao.getAll().size());
    }

    @Test
    public void createExistentEmail(){
        UserService service = getService();
        UserDao dao = service.getDao();
        int size = dao.getAll().size();
    }

    private UserService getService(){
        return new UserService(db.getTestDatabase(), tableName);
    }

}
