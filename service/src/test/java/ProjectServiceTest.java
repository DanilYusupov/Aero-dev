import com.gdc.aerodev.dao.postgres.PostgresProjectDao;
import com.gdc.aerodev.model.Project;
import com.gdc.aerodev.model.ProjectType;
import com.gdc.aerodev.service.postgres.ProjectService;
import com.opentable.db.postgres.embedded.FlywayPreparer;
import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.PreparedDbRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProjectServiceTest {

    private String tableName = "project_test";
    private String projectName = "Project";
    private Long projectOwner = 1L;
    private ProjectType projectType = ProjectType.AERODYNAMICS;
    private String projectDescription = "Description...";

    @Rule
    public PreparedDbRule db = EmbeddedPostgresRules.preparedDatabase(FlywayPreparer.forClasspathLocation("project-service"));

    //Create Project tests

    @Test
    public void testCreateProject(){
        ProjectService service = getService();
        PostgresProjectDao dao = service.getDao();
        int size = dao.count();
        assertNotNull(service.createProject(projectName, projectOwner, projectType, projectDescription));
        assertEquals(++size, dao.count());
    }

    @Test
    public void testCreateExistentProject(){
        ProjectService service = getService();
        PostgresProjectDao dao = service.getDao();
        assertNotNull(service.createProject(projectName, projectOwner, projectType, projectDescription));
        int size = dao.count();
        assertNull(service.createProject(projectName, projectOwner, projectType, projectDescription));
        assertEquals(size, dao.count());
    }

    @Test
    public void testCreateWithEmptyName(){
        ProjectService service = getService();
        PostgresProjectDao dao = service.getDao();
        int size = dao.count();
        assertNull(service.createProject("", projectOwner, projectType, projectDescription));
        assertEquals(size, dao.count());
    }

    //Update Project test

    @Test
    public void testUpdateProject(){
        ProjectService service = getService();
        PostgresProjectDao dao = service.getDao();
        Project before = dao.getById(1L);
        assertNotNull(service.updateProject(1L, projectName, projectType, projectDescription));
        assertNotEquals(before.getProjectName(), dao.getById(1L).getProjectName());
    }

    @Test
    public void testUpdateExistentProject(){
        ProjectService service = getService();
        PostgresProjectDao dao = service.getDao();
        Long id = service.createProject(projectName, projectOwner, projectType, projectDescription);
        int size = dao.count();
        assertNull(service.updateProject(id, "", projectType, ""));
        assertEquals(size, dao.count());
    }

    private ProjectService getService(){
        return new ProjectService(db.getTestDatabase(), tableName);
    }
}
