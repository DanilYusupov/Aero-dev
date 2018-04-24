package com.gdc.aerodev.dao.test.postgres;

import com.gdc.aerodev.dao.ProjectImageDao;
import com.gdc.aerodev.dao.postgres.PostgresProjectDao;
import com.gdc.aerodev.dao.postgres.PostgresProjectImageDao;
import com.gdc.aerodev.model.ProjectImage;
import com.opentable.db.postgres.embedded.FlywayPreparer;
import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.PreparedDbRule;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PostgresProjectImageDaoTest extends WithFiles{

    private final String tableName = "aero.project_image_test";
    private Long imageId = 1L;
    private Long projectId = 1L;
    private String contentType = "img";

    @Rule
    public PreparedDbRule db = EmbeddedPostgresRules.preparedDatabase(FlywayPreparer.forClasspathLocation("project_image"));

    @Test
    public void testInsertImage() throws IOException {
        ProjectImageDao dao = getDao();
        ProjectImage image = new ProjectImage(projectId, getImage(), contentType);
        assertTrue(image.getProjectImage().length > 0);
        assertEquals(imageId, dao.save(image));
    }

    @Test (expected = NullPointerException.class)
    public void testInsertNullImage() {
        ProjectImageDao dao = getDao();
        ProjectImage image = new ProjectImage(projectId, null, contentType);
        dao.save(image);
    }

    @Test
    public void testGetImages() throws IOException {
        ProjectImageDao dao = getDao();
        ProjectImage image0 = new ProjectImage(projectId, getImage(), contentType);
        ProjectImage image1 = new ProjectImage(projectId, getImage(), contentType);
        ProjectImage image2 = new ProjectImage(projectId, getImage(), contentType);
        Long id0 = dao.save(image0);
        Long id1 = dao.save(image1);
        Long id2 = dao.save(image2);
        List<ProjectImage> received = dao.getAll(projectId);
        assertEquals(3, received.size());
        assertEquals(id0, received.get(0).getImageId());
        assertEquals(id1, received.get(1).getImageId());
        assertEquals(id2, received.get(2).getImageId());
    }

    @Test
    public void testGetNonExistentImage(){
        ProjectImageDao dao = getDao();
        List<ProjectImage> received = dao.getAll(projectId);
        assertTrue(received.isEmpty());
    }

    @Test
    public void testDelete(){
        ProjectImageDao dao = getDao();
        assertFalse(dao.delete(-1L));
    }

    @Test
    public void deleteNonExistent(){

    }

    private ProjectImageDao getDao() {
        return new PostgresProjectImageDao(new JdbcTemplate(db.getTestDatabase()), tableName);
    }

}
