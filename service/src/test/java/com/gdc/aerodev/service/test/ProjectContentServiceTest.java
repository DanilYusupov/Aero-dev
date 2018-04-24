package com.gdc.aerodev.service.test;

import com.gdc.aerodev.dao.postgres.PostgresProjectContentDao;
import com.gdc.aerodev.model.ProjectContent;
import com.gdc.aerodev.service.ProjectContentService;
import com.gdc.aerodev.service.impl.ProjectContentServiceImpl;
import com.opentable.db.postgres.embedded.FlywayPreparer;
import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.PreparedDbRule;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ProjectContentServiceTest extends WithFiles {

    private String tableName = "aero.project_content";
    private Long projectId = 1L;
    private String description = "Some text with description here...";

    @Rule
    public PreparedDbRule db = EmbeddedPostgresRules.preparedDatabase(FlywayPreparer.forClasspathLocation("project-content-service"));

    @Test
    public void createProjectContentTest() throws IOException {
        ProjectContentService service = getService();
        assertTrue(service.createProjectContent(projectId, getImage(), description, new Date()));
    }

    @Test
    public void updateProjectContentTest() throws IOException {
        ProjectContentService service = getService();
        service.createProjectContent(projectId, getImage(), description, new Date());
        String newDescription = "New description...";
        assertTrue(service.updateProjectContent(projectId, getImage(), newDescription));
    }

    @Test
    public void getProjectContentTest() throws IOException {
        ProjectContentService service = getService();
        service.createProjectContent(projectId, getImage(), description, new Date());
        assertEquals(description, service.get(projectId).getProjectDescription());
    }

    @Test(expected = NullPointerException.class)
    public void createProjectContentWithEmptyId() throws IOException {
        ProjectContentService service = getService();
        service.createProjectContent(null, getImage(), description, new Date());
    }

    @Test
    public void createProjectContentWithEmptyField() throws IOException {
        ProjectContentService service = getService();
        service.createProjectContent(projectId, getImage(), null, new Date());
        assertFalse(service.createProjectContent(projectId, getImage(), "", new Date()));
    }

    @Test
    public void createProjectContentTwice() throws IOException {
        ProjectContentService service = getService();
        service.createProjectContent(projectId, getImage(), description, new Date());
        service.createProjectContent(projectId, getImage(), description, new Date());
    }

    @Test
    public void getNullLogo(){
        ProjectContentService service = getService();
        boolean create = service.createProjectContent(projectId, new byte[0], description, new Date());
        assertTrue(create);
        boolean result = service.get(projectId).getProjectLogo().length > 0;
        assertTrue(result);
    }

    ProjectContentService getService() {
        return new ProjectContentServiceImpl(new PostgresProjectContentDao(new JdbcTemplate(db.getTestDatabase()), tableName));
    }
}
