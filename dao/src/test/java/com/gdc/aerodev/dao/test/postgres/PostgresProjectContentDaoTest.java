package com.gdc.aerodev.dao.test.postgres;

import com.gdc.aerodev.dao.ProjectContentDao;
import com.gdc.aerodev.dao.postgres.PostgresProjectContentDao;
import com.gdc.aerodev.model.ProjectContent;
import com.opentable.db.postgres.embedded.FlywayPreparer;
import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.PreparedDbRule;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.util.Date;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.*;

public class PostgresProjectContentDaoTest extends WithFiles {

    private final String tableName = "aero.project_content";
    private Long projectId = 1L;
    private String projectDescription = "Some description";

    @Rule
    public PreparedDbRule db = EmbeddedPostgresRules.preparedDatabase(FlywayPreparer.forClasspathLocation("project_content"));

    @Test
    public void testInsert() throws IOException {
        ProjectContentDao dao = getDao();
        ProjectContent content = new ProjectContent(projectId, getImage(), projectDescription, new java.util.Date());
        Long id = dao.save(content);
        assertNotNull(id);
        ProjectContent projectContent = dao.getById(id);
        assertTrue(projectContent.getProjectLogo().length > 0);
    }

    @Test
    public void testUpdate() throws IOException {
        ProjectContentDao dao = getDao();
        ProjectContent content = new ProjectContent(projectId, getImage(), projectDescription, new Date());
        Long id = dao.save(content);
        Date first = content.getProjectBirthDay();
        assertEquals(projectDescription, dao.getById(id).getProjectDescription());
        id = dao.save(new ProjectContent(id, getImage(), "Wow!", new Date()));
        assertNotEquals(projectDescription, dao.getById(id).getProjectDescription());
        assertNotEquals(first, dao.getById(id).getProjectBirthDay());
    }

    @Test
    public void testDelete() throws IOException {
        ProjectContentDao dao = getDao();
        ProjectContent content = new ProjectContent(projectId, getImage(), projectDescription, new java.util.Date());
        Long id = dao.save(content);
        assertTrue(dao.delete(id));
    }

    @Test
    public void testGetByIdNonExistent() {
        ProjectContentDao dao = getDao();
        assertNull(dao.getById(-1L));
    }

    @Test
    public void testDeleteNonExistentUser(){
        ProjectContentDao dao = getDao();
        assertFalse(dao.delete(-1L));
    }

    private ProjectContentDao getDao() {
        return new PostgresProjectContentDao(new JdbcTemplate(db.getTestDatabase()), tableName);
    }

}
