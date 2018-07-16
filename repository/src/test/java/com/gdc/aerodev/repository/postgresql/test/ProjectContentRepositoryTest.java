package com.gdc.aerodev.repository.postgresql.test;

import com.gdc.aerodev.model.Project;
import com.gdc.aerodev.model.ProjectContent;
import com.gdc.aerodev.repository.postgresql.ProjectContentRepository;
import com.gdc.aerodev.repository.postgresql.ProjectRepository;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@AutoConfigureEmbeddedDatabase
@FlywayTest
@DataJpaTest
public class ProjectContentRepositoryTest {

    @Autowired
    private ProjectContentRepository repository;
    @Autowired
    private ProjectRepository projectRepository;

    private Long prjTwoId = 2L;
    private String description = "Awesome project...";
    private Date date = new Date();

    /**
     * For @OneToOne association You must save entities to each other before persist:
     * Set child in parent entity and parent in child entity.
     */

    @Test
    public void createContentTest(){
        createContent();
        assertEquals(1, repository.findAll().size());
    }

    @Test
    public void getContentTest(){
        createContent();
        Project project = projectRepository.findByProjectId(prjTwoId);
        ProjectContent content = repository.findByProject(project);
        assertEquals(date, content.getProjectBirthDay());
        assertEquals(description, content.getProjectDescription());
        assertEquals(project, content.getProject());
    }

    @Test
    public void updateContentTest(){
        description = "new description...";
        createContent();
        Project project = projectRepository.findByProjectId(prjTwoId);
        ProjectContent content = repository.findByProject(project);
        content.setProjectDescription(description);
        repository.save(content);
        assertEquals(description, repository.findByProject(project).getProjectDescription());
    }

    @Test
    public void deleteTest(){
        createContent();
        projectRepository.deleteById(prjTwoId);
        assertEquals(0, repository.findAll().size());
    }

    //Abnormal tests

    @Test
    public void getByNullProjectTest(){
        createContent();
        assertNull(repository.findByProject(null));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveNullProjectTest(){
        ProjectContent content = new ProjectContent(null, description, date);
        repository.save(content);
    }

    private void createContent(){
        Project project = projectRepository.findByProjectId(prjTwoId);
        ProjectContent content = new ProjectContent(null, description, date);
        project.setContent(content);
        content.setProject(project);
        projectRepository.save(project);
    }

}
