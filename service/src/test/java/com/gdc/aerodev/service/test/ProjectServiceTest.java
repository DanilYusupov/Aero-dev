package com.gdc.aerodev.service.test;

import com.gdc.aerodev.model.Project;
import com.gdc.aerodev.model.ProjectType;
import com.gdc.aerodev.service.ProjectService;
import com.opentable.db.postgres.embedded.FlywayPreparer;
import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.PreparedDbRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectServiceTest {

    @Autowired
    private ProjectService service;

    /**
     * Name of table according to classpath:/project-service/V1__Create_test_table.sql
     */
    private String tableName = "project_test";
    private String projectName = "Project";
    private Long projectOwner = 1L;
    private ProjectType projectType = ProjectType.AERODYNAMICS;

    @Rule
    public PreparedDbRule db = EmbeddedPostgresRules.preparedDatabase(FlywayPreparer.forClasspathLocation("project-service"));

    //Create Project tests

    @Test
    public void testCreateProject(){
        long size = service.countProjects();
        assertNotNull(service.createProject(projectName, projectOwner, projectType));
        assertEquals(++size, service.countProjects());
    }

    @Test
    public void testCreateExistentProject(){
        assertNotNull(service.createProject(projectName, projectOwner, projectType));
        long size = service.countProjects();
        assertNull(service.createProject(projectName, projectOwner, projectType));
        assertEquals(size, service.countProjects());
    }

    @Test
    public void testCreateWithEmptyName(){
        long size = service.countProjects();
        assertNull(service.createProject("", projectOwner, projectType));
        assertEquals(size, service.countProjects());
    }

    //Update Project test

    @Test
    public void testUpdateProject(){
        Project before = service.getProject(1L);
        assertNotNull(service.updateProject(1L, projectName, projectType));
        assertNotEquals(before.getProjectName(), service.getProject(1L).getProjectName());
    }

    @Test (expected = NullPointerException.class)
    public void testUpdateNonExistentProject(){
        service.updateProject(-1L, "", projectType);
    }

//    private ProjectService getService(){
//        return new ProjectServiceImpl(new PostgresProjectDao(new JdbcTemplate(db.getTestDatabase()), tableName));
//    }
}
