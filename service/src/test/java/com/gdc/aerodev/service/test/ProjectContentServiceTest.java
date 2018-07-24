package com.gdc.aerodev.service.test;

import com.gdc.aerodev.model.ProjectContent;
import com.gdc.aerodev.repository.postgresql.ProjectContentRepository;
import com.gdc.aerodev.repository.postgresql.ProjectRepository;
import com.gdc.aerodev.service.ProjectContentService;
import com.opentable.db.postgres.embedded.FlywayPreparer;
import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.PreparedDbRule;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ProjectContentServiceTest extends WithFiles {

    @Autowired
    private ProjectContentService service;

    @MockBean
    private ProjectContentRepository repository;
    @MockBean
    private ProjectRepository projectRepository;

    /**
     * Name of table according to classpath:/project-content-service/V1__Create_test_table.sql
     */
    private String tableName = "aero.project_content";
    private final Long DEFAULT_PROJECT_ID = 1L;
    private final int DEFAULT_LOGO_SIZE = 39576;
    private Long projectId = 2L;
    private String description = "Some text with description here...";

    @Rule
    public PreparedDbRule db = EmbeddedPostgresRules.preparedDatabase(FlywayPreparer.forClasspathLocation("project-content-service"));

    @Test
    public void createProjectContentTest() throws IOException {
        assertTrue(service.createProjectContent(projectId, getImage(), description, new Date()));
    }

    @Test
    public void updateProjectContentTest() throws IOException {
        service.createProjectContent(projectId, getImage(), description, new Date());
        String newDescription = "New description...";
        assertTrue(service.updateProjectContent(projectId, getImage(), newDescription));
    }

    @Test
    public void getProjectContentTest() throws IOException {
        service.createProjectContent(projectId, getImage(), description, new Date());
        ProjectContent content = service.get(projectId);
//        System.out.println(content.getProjectBirthDay());
        assertEquals(description, content.getProjectDescription());
        assertTrue(content.getProjectLogo().length > 0);
    }

    @Test(expected = NullPointerException.class)
    public void createProjectContentWithEmptyId() throws IOException {
        service.createProjectContent(null, getImage(), description, new Date());
    }

    @Test
    public void createProjectContentWithEmptyField() throws IOException {
        service.createProjectContent(projectId, getImage(), null, new Date());
        assertFalse(service.createProjectContent(projectId, getImage(), "", new Date()));
    }

    @Test
    public void createProjectContentTwice() throws IOException {
        service.createProjectContent(projectId, getImage(), description, new Date());
        service.createProjectContent(projectId, getImage(), description, new Date());
    }

    @Test
    public void getNullLogo() {
        boolean create = service.createProjectContent(projectId, new byte[0], description, new Date());
        assertTrue(create);
        ProjectContent content = service.get(projectId);
//        System.out.println(content.getProjectLogo().length + " bytes...");
        assertTrue(content.getProjectLogo().length == DEFAULT_LOGO_SIZE);
    }

//    ProjectContentService getService() {
//        return new ProjectContentServiceImpl(new PostgresProjectContentDao(new JdbcTemplate(db.getTestDatabase()), tableName));
//    }
}
