package com.gdc.aerodev.repository.postgresql.test;

import com.gdc.aerodev.model.Project;
import com.gdc.aerodev.model.ProjectType;
import com.gdc.aerodev.model.User;
import com.gdc.aerodev.repository.postgresql.ProjectRepository;
import com.gdc.aerodev.repository.postgresql.UserRepository;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@AutoConfigureEmbeddedDatabase
@FlywayTest
@DataJpaTest
public class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository repository;
    @Autowired
    private UserRepository userRepository;
    /**
     * Params below are taken from migration file.
     */
    private String prj1Name = "Discover #1";
    private String prj2Name = "Discover #2";
    private Long prjOneId = 1L;
    private Long prjOwnerId = 1L;
    private int ownerPrjsCount = 3;

    private String name = "New issue";
    private ProjectType type = ProjectType.AEROELASTICITY;

    @Test
    public void findByIdTest(){
        Project project = repository.findByProjectId(prjOneId);
        assertEquals(prj1Name, project.getProjectName());
        assertEquals(userRepository.findByUserId(prjOwnerId), project.getOwner());
    }

    @Test
    public void findByNameTest(){
        Project project = repository.findByProjectName(prj2Name);
        assertEquals(userRepository.findByUserId(prjOwnerId), project.getOwner());
    }

    @Test
    public void findAllByOwnerTest(){
        User user = userRepository.findByUserId(prjOwnerId);
        List<Project> projects = repository.findAllByOwner(user);
        assertEquals(3, projects.size());
        assertEquals(prj2Name, projects.get(1).getProjectName());
    }

    @Test
    public void createProject(){
        User user = userRepository.findByUserId(prjOwnerId);
        Project project = new Project(name, type, user);
        project = repository.save(project);
        assertEquals(ownerPrjsCount + 1, repository.findAllByOwner(user).size());
        assertEquals(name, repository.findByProjectId(project.getProjectId()).getProjectName());
    }

    @Test
    public void updateProject(){
        name = "Sensation";
        Project project = repository.findByProjectId(prjOneId);
        project.setProjectName(name);
        project = repository.save(project);
        assertEquals(name, project.getProjectName());
        assertEquals(ownerPrjsCount, repository.findAllByOwner(userRepository.findByUserId(prjOwnerId)).size());
    }

    @Test
    public void deleteTest(){
        repository.deleteById(prjOneId);
        assertEquals(ownerPrjsCount - 1, repository.findAllByOwner(userRepository.findByUserId(prjOwnerId)).size());
    }

    //Abnormal tests

    @Test
    public void getFakeIdTest(){
        assertNull(repository.findByProjectId(0L));
        assertNull(repository.findByProjectId(null));
    }

    @Test
    public void getByFakeNameTest(){
        assertNull(repository.findByProjectName(""));
        assertNull(repository.findByProjectName(null));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveExistentNameTest(){
        User user = userRepository.findByUserId(prjOwnerId);
        Project project = new Project(prj1Name, type, user);
        repository.save(project);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveNullOwnerTest(){
        Project project = new Project(name, type, null);
        repository.save(project);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void deleteNonExistentProjectTest(){
        repository.deleteById(0L);
    }

}
