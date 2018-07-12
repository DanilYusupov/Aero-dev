package com.gdc.aerodev.repository.postgresql.test;

import com.gdc.aerodev.model.Project;
import com.gdc.aerodev.model.ProjectFile;
import com.gdc.aerodev.repository.postgresql.ProjectFileRepository;
import com.gdc.aerodev.repository.postgresql.ProjectRepository;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import javafx.scene.shape.Path;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.nio.file.Files;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@AutoConfigureEmbeddedDatabase
@FlywayTest
@DataJpaTest
public class ProjectFileRepositoryTest {

    @Autowired
    private ProjectFileRepository repository;

    @Autowired
    private ProjectRepository projectRepository;

    private Long prjThreeId = 3L;
    private File inputFile = new File(getClass().getResource("/file/test.jpg").getPath());
    private File outputFile = new File(getClass().getResource("/file/").getPath() + "out.jpg");
    private int count = 3;

    @Test
    public void createFileTest() {
        Project project = projectRepository.findByProjectId(prjThreeId);
        ProjectFile file = createFile();
        assertEquals(1, repository.findAll().size());
//        System.err.println("MIME type: " + file.getContentType());
//        saveFile(file.getFile());
    }

    @Test
    public void createFileBatchTest(){
        for (int i = 0; i < count; i++) {
            createFile();
        }
        assertEquals(count, repository.findAll().size());
    }

    @Test
    public void getByProjectTest(){
        Project project = projectRepository.findByProjectId(prjThreeId);
        for (int i = 0; i < count; i++) {
            createFile();
        }
        assertEquals(count, repository.findAllByProject(project).size());
    }

    @Test
    public void deleteTest(){
        ProjectFile file = createFile();
        repository.delete(file);
        assertEquals(0, repository.findAll().size());
        file = createFile();
        repository.deleteById(file.getFileId());
        assertEquals(0, repository.findAll().size());
    }

    @Test
    public void cascadeDeleteTest(){
        for (int i = 0; i < count; i++) {
            createFile();
        }
        projectRepository.deleteById(prjThreeId);
        assertEquals(0, repository.findAll().size());
    }

    //Abnormal tests

    @Test
    public void getByFakeId(){
        assertEquals(Optional.empty(), repository.findById(0L));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveNullFileTest(){
        Project project = projectRepository.findByProjectId(prjThreeId);
        ProjectFile file = new ProjectFile(null, "");
        file.setProject(project);
        repository.save(file);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveProjectNullTest(){
        ProjectFile file = new ProjectFile(new byte[]{}, "");
        repository.save(file);
    }

    private ProjectFile createFile() {
        Project project = projectRepository.findByProjectId(prjThreeId);
        ProjectFile file;
        try {
            file = new ProjectFile(getFile(), Files.probeContentType(inputFile.toPath()));
            file.setProject(project);
            file = repository.save(file);
            return file;
        } catch (IOException e) {
            throw new RuntimeException("Cannot get MIME type of file: '" + inputFile.getAbsolutePath() + "'.", e);
        }
    }

    private byte[] getFile() {
        try (
                BufferedInputStream input = new BufferedInputStream(new FileInputStream(inputFile));
                ByteArrayOutputStream out = new ByteArrayOutputStream()
        ) {
            int a;
            while ((a = input.read()) != -1) {
                out.write(a);
            }
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Cannot load file: '" + inputFile.getAbsolutePath() + "'.", e);
        }
    }

    private void saveFile(byte[] data){
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(outputFile))) {
            out.write(data);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException("Cannot save file to: '" + outputFile.getAbsolutePath() + "'.", e);
        }
    }
}
