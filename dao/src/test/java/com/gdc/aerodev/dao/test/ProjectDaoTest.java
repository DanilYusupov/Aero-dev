package com.gdc.aerodev.dao.test;

import com.gdc.aerodev.dao.ProjectDao;
import com.gdc.aerodev.model.Project;
import com.gdc.aerodev.model.ProjectType;
import com.opentable.db.postgres.embedded.FlywayPreparer;
import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.PreparedDbRule;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProjectDaoTest {

    private String tableName = "project_test";
    private String name = "start-up";
    private Long owner = 1L;
    private ProjectType type = ProjectType.Aerodynamics;
    private String description = "This is a new project of...";
    private Project project = new Project(name, owner, type, description);

    @Rule
    public PreparedDbRule db = EmbeddedPostgresRules.preparedDatabase(FlywayPreparer.forClasspathLocation("project"));

    //Standard tests

    @Test
    public void testGetById() {
        ProjectDao dao = getDao();
        Project project = dao.getById(1L);
        assertEquals("3D test", project.getProjectName());
        assertEquals(ProjectType.Design, project.getProjectType());
        assertEquals(Long.valueOf(1), project.getProjectOwner());
    }

    @Test
    public void testGetByName() {
        ProjectDao dao = getDao();
        Project project = dao.getByName("3D test");
        assertEquals(ProjectType.Design, project.getProjectType());
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
        ProjectType type = ProjectType.Aerodynamics;
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


    private ProjectDao getDao() {
        return new ProjectDao(new JdbcTemplate(db.getTestDatabase()), tableName);
    }
}
