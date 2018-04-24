package com.gdc.aerodev.dao.test.postgres;

import com.gdc.aerodev.dao.AvatarDao;
import com.gdc.aerodev.dao.postgres.PostgresAvatarDao;
import com.gdc.aerodev.model.Avatar;
import com.opentable.db.postgres.embedded.FlywayPreparer;
import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.PreparedDbRule;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class PostgresAvatarDaoTest extends WithFiles {

    private final String tableName = "avatar_test";
    private Long id = 1L;
    private Long owner = 5L;

    @Rule
    public PreparedDbRule db = EmbeddedPostgresRules.preparedDatabase(FlywayPreparer.forClasspathLocation("avatar"));

    @Test
    public void testInsertImage() throws IOException {
        AvatarDao dao = getDao();
        Avatar avatar = new Avatar(owner, getImage(), "image");
        assertTrue(avatar.getAvatarData().length > 0);
        assertEquals(id, dao.save(avatar));
    }

    @Test (expected = NullPointerException.class)
    public void testInsertNullImage() {
        AvatarDao dao = getDao();
        Avatar avatar = new Avatar(owner, null, "image");
        dao.save(avatar);
    }

    @Test
    public void testGetImage() throws IOException {
        AvatarDao dao = getDao();
        Avatar avatar = new Avatar(owner, getImage(), "image");
        Long id = dao.save(avatar);
        Avatar received = dao.getById(owner);
        assertEquals(avatar.getAvatarData().length, received.getAvatarData().length);
        assertEquals(id, received.getAvatarId());
    }

    @Test
    public void testGetNonExistentImage(){
        AvatarDao dao = getDao();
        assertNull(dao.getById(owner));
    }

    @Test
    public void testUpdateImage() throws IOException {
        Long newOwner = 77L;
        AvatarDao dao = getDao();
        Avatar avatar = new Avatar(owner, getImage(), "image");
        dao.save(avatar);
        Avatar update = dao.getById(owner);
        update.setAvatarOwner(newOwner);
        assertEquals(id, dao.save(update));
        assertEquals(newOwner, dao.getById(newOwner).getAvatarOwner());
    }

    private AvatarDao getDao() {
        return new PostgresAvatarDao(new JdbcTemplate(db.getTestDatabase()), tableName);
    }
}
