package com.gdc.aerodev.service.test;

import com.gdc.aerodev.dao.postgres.PostgresProjectDao;
import com.gdc.aerodev.model.Project;
import com.gdc.aerodev.model.ProjectType;
import com.gdc.aerodev.service.ProjectService;
import com.gdc.aerodev.service.impl.ProjectServiceImpl;
import com.opentable.db.postgres.embedded.FlywayPreparer;
import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.PreparedDbRule;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.Assert.*;

public class ProjectServiceTest {

    private String tableName = "project_test";
    private String projectName = "Project";
    private Long projectOwner = 1L;
    private ProjectType projectType = ProjectType.AERODYNAMICS;

    @Rule
    public PreparedDbRule db = EmbeddedPostgresRules.preparedDatabase(FlywayPreparer.forClasspathLocation("project-service"));

    //Create Project tests

    @Test
    public void testCreateProject(){
        ProjectService service = getService();
        int size = service.countProjects();
        assertNotNull(service.createProject(projectName, projectOwner, projectType));
        assertEquals(++size, service.countProjects());
    }

    @Test
    public void testCreateExistentProject(){
        ProjectService service = getService();
        assertNotNull(service.createProject(projectName, projectOwner, projectType));
        int size = service.countProjects();
        assertNull(service.createProject(projectName, projectOwner, projectType));
        assertEquals(size, service.countProjects());
    }

    @Test
    public void testCreateWithEmptyName(){
        ProjectService service = getService();
        int size = service.countProjects();
        assertNull(service.createProject("", projectOwner, projectType));
        assertEquals(size, service.countProjects());
    }

    //Update Project test

    @Test
    public void testUpdateProject(){
        ProjectService service = getService();
        Project before = service.getProject(1L);
        assertNotNull(service.updateProject(1L, projectName, projectType));
        assertNotEquals(before.getProjectName(), service.getProject(1L).getProjectName());
    }

    @Test (expected = NullPointerException.class)
    public void testUpdateNonExistentProject(){
        ProjectService service = getService();
        service.updateProject(-1L, "", projectType);
    }

    private ProjectService getService(){
        return new ProjectServiceImpl(new PostgresProjectDao(new JdbcTemplate(db.getTestDatabase()), tableName));
    }
}
