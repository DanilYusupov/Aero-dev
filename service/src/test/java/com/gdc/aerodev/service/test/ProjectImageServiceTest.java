package com.gdc.aerodev.service.test;

import com.gdc.aerodev.dao.postgres.PostgresProjectImageDao;
import com.gdc.aerodev.model.ProjectImage;
import com.gdc.aerodev.service.ProjectImageService;
import com.gdc.aerodev.service.impl.ProjectImageServiceImpl;
import com.opentable.db.postgres.embedded.FlywayPreparer;
import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.PreparedDbRule;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class ProjectImageServiceTest extends WithFiles{

    private String tableName = "aero.project_image_test";
    private Long projectId = 1L;
    private String contentType = "image";

    @Test
    public void createProjectImageTest() throws IOException {
        ProjectImageService service = getService();
        assertNotNull(service.createImage(projectId, getImage(), contentType));
    }

    @Test
    public void deleteProjectImageTest() throws IOException {
        ProjectImageService service = getService();
        Long imgId = service.createImage(projectId, getImage(), contentType);
        assertTrue(service.deleteImage(imgId));
    }

    @Test
    public void getAllTest() throws IOException {
        ProjectImageService service = getService();
        Long imgId0 = service.createImage(projectId, getImage(), contentType);
        Long imgId1 = service.createImage(projectId, getImage(), contentType);
        Long imgId2 = service.createImage(projectId, getImage(), contentType);
        List<ProjectImage> list = service.getAll(projectId);
        assertEquals(3, list.size());
        assertEquals(imgId2, list.get(2).getImageId());
    }

    @Test
    public void getTest() throws IOException {
        ProjectImageService service = getService();
        Long imgId0 = service.createImage(projectId, getImage(), contentType);
        assertEquals(getImage().length, service.get(imgId0).getProjectImage().length);
    }

    @Test (expected = NullPointerException.class)
    public void createNullImage(){
        ProjectImageService service = getService();
        service.createImage(projectId, null, contentType);
    }

    @Test (expected = NullPointerException.class)
    public void createEmptyImage(){
        ProjectImageService service = getService();
        Long id = service.createImage(projectId, new byte[0], contentType);
        assertTrue(id > 0);
    }

    @Rule
    public PreparedDbRule db = EmbeddedPostgresRules.preparedDatabase(FlywayPreparer.forClasspathLocation("project-image-service"));

    private ProjectImageService getService(){
        return new ProjectImageServiceImpl(new PostgresProjectImageDao(new JdbcTemplate(db.getTestDatabase()), tableName));
    }
}
