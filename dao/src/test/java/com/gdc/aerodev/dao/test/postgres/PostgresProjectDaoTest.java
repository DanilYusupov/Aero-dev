package com.gdc.aerodev.dao.test.postgres;

import com.gdc.aerodev.dao.ProjectDao;
import com.gdc.aerodev.dao.postgres.PostgresProjectDao;
import com.gdc.aerodev.dao.exception.DaoException;
import com.gdc.aerodev.model.Project;
import com.gdc.aerodev.model.ProjectType;
import com.opentable.db.postgres.embedded.FlywayPreparer;
import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.PreparedDbRule;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class PostgresProjectDaoTest {
    /**
     * Name of table according to classpath:/project/V1__Create_test_table.sql
     */
    private String tableName = "project_test";
    private String name = "start-up";
    private Long owner = 1L;
    private ProjectType type = ProjectType.AERODYNAMICS;
    private Project project = new Project(name, owner, type);

    @Rule
    public PreparedDbRule db = EmbeddedPostgresRules.preparedDatabase(FlywayPreparer.forClasspathLocation("project"));

    //Standard tests

    @Test
    public void testGetById() {
        ProjectDao dao = getDao();
        Project project = dao.getById(1L);
        assertEquals("3D test", project.getProjectName());
        assertEquals(ProjectType.DESIGN, project.getProjectType());
        assertEquals(Long.valueOf(1), project.getProjectOwner());
    }

    @Test
    public void testGetByName() {
        ProjectDao dao = getDao();
        Project project = dao.getByName("3D test");
        assertEquals(ProjectType.DESIGN, project.getProjectType());
        assertEquals(Long.valueOf(1), project.getProjectOwner());
    }

    @Test
    public void testGetAll() {
        ProjectDao dao = getDao();
        List<Project> list = dao.getAll();
        assertEquals(3, list.size());
    }

    @Test
    public void testInsert() {
        ProjectDao dao = getDao();
        Long id = dao.save(project);
        assertEquals(name, dao.getById(id).getProjectName());
    }

    @Test
    public void testUpdate() {
        ProjectDao dao = getDao();
        Long id = 1L;
        String name = "Thing";
        ProjectType type = ProjectType.AERODYNAMICS;
        Project project = dao.getById(id);
        assertEquals("3D test", project.getProjectName());
        project.setProjectName(name);
        project.setProjectType(type);
        dao.save(project);
        assertEquals(name, dao.getById(id).getProjectName());
        assertEquals(type, dao.getById(id).getProjectType());
    }

    @Test
    public void testDelete() {
        ProjectDao dao = getDao();
        int size = dao.getAll().size();
        dao.delete(2L);
        assertEquals(size - 1, dao.getAll().size());
    }

    @Test
    public void testCount(){
        ProjectDao dao = getDao();
        assertEquals(3, dao.count());
    }

    //Abnormal tests

    @Test
    public void testGetByIdNonExistent() {
        ProjectDao dao = getDao();
        assertNull(dao.getById(-1L));
    }

    @Test
    public void testGetByNameNonExistent() {
        ProjectDao dao = getDao();
        assertNull(dao.getByName("!!!"));
    }

    @Test(expected = DaoException.class)
    public void testInsertExistentProjectException() {
        ProjectDao dao = getDao();
        String newName = "3D test";
        project.setProjectName(newName);
        assertNull(dao.save(project));
    }

    @Test
    public void testInsertExistentUserDbSize() {
        ProjectDao dao = getDao();
        String newName = "3D test";
        project.setProjectName(newName);
        int size = dao.getAll().size();
        try {
            assertNull(dao.save(project));
        } catch (DaoException e) {
            assertEquals(size, dao.getAll().size());
        }
    }

    @Test
    public void testDeleteNonExistentUser(){
        ProjectDao dao = getDao();
        assertFalse(dao.delete(-1L));
    }

    private ProjectDao getDao() {
        return new PostgresProjectDao(new JdbcTemplate(db.getTestDatabase()), tableName);
    }
}
