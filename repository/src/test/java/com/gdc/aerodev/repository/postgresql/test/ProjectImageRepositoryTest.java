package com.gdc.aerodev.repository.postgresql.test;

import com.gdc.aerodev.model.Project;
import com.gdc.aerodev.model.ProjectImage;
import com.gdc.aerodev.repository.postgresql.ProjectImageRepository;
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

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@AutoConfigureEmbeddedDatabase
@FlywayTest
@DataJpaTest
public class ProjectImageRepositoryTest {

    @Autowired
    private ProjectImageRepository repository;

    @Autowired
    private ProjectRepository projectRepository;

    private Long prjThreeId = 3L;
    private int count = 3;

    @Test
    public void createImageTest() {
        createImage();
        assertEquals(1, repository.findAll().size());
    }

    @Test
    public void createImageBatchTest(){
        for (int i = 0; i < count; i++) {
            createImage();
        }
        assertEquals(count, repository.findAll().size());
    }

    @Test
    public void getByProjectTest(){
        Project project = projectRepository.findByProjectId(prjThreeId);
        for (int i = 0; i < count; i++) {
            createImage();
        }
        assertEquals(count, repository.findAllByProject(project).size());
    }

    @Test
    public void deleteByProjectTest(){
        for (int i = 0; i < count; i++) {
            createImage();
        }
        projectRepository.deleteById(prjThreeId);
        assertEquals(0, repository.findAll().size());
    }

    @Test
    public void deleteTest(){
        ProjectImage image = createImage();
        repository.delete(image);
        assertEquals(0, repository.findAll().size());
        image = createImage();
        repository.deleteById(image.getImageId());
        assertEquals(0, repository.findAll().size());
    }

    //Abnormal tests

    @Test
    public void getByFakeIdTest(){
        createImage();
        assertNull(repository.findByImageId(0L));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveNullByteaTest(){
        Project project = projectRepository.findByProjectId(prjThreeId);
        ProjectImage image = new ProjectImage(null, "jpg");
        image.setProject(project);
        repository.save(image);
    }

    private ProjectImage createImage() {
        Project project = projectRepository.findByProjectId(prjThreeId);
        ProjectImage image = new ProjectImage(new byte[]{}, "jpg");
        image.setProject(project);
        repository.save(image);
        return image;
    }

}