package com.gdc.aerodev.dao.test.postgres;

import com.gdc.aerodev.dao.CrDao;
import com.gdc.aerodev.dao.exception.DaoException;
import com.gdc.aerodev.dao.postgres.PostgresCrDao;
import com.gdc.aerodev.model.Cr;
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
import static org.junit.Assert.assertNotNull;

public class PostgresCrDaoTest {

    /**
     * Name of table according to classpath:/user/V1__Create_test_table.sql
     */
    private String tableName = "crs_test";
    private String name = "Robert";
    private String pass = "glv";
    private String email = "rglv@mail.com";
    private Long compId = 1L;
    private String firstName = "Robert";
    private String lastName = "Vorb";
    private String position = "Compliance manager";
    private Cr cr = new Cr(name, pass, email, compId, firstName, lastName, position);

    @Rule
    public PreparedDbRule db = EmbeddedPostgresRules.preparedDatabase(FlywayPreparer.forClasspathLocation("comp_reps"));

    @Test
    public void testGetById() {
        CrDao dao = getDao();
        Cr cr = dao.getById(1L);
        assertEquals("Bill", cr.getCrName());
        assertEquals("HR Lead", cr.getCrPosition());
        assertEquals(Long.valueOf(31), cr.getCompanyId());
    }

    @Test
    public void testGetByName() {
        CrDao dao = getDao();
        Cr cr = dao.getByName("Mattew");
        assertEquals("Manager", cr.getCrPosition());
        assertEquals(Long.valueOf(63), cr.getCompanyId());
    }

    @Test
    public void testGetAll() {
        CrDao dao = getDao();
        List<Cr> list = dao.getAll();
        assertEquals(3, list.size());
    }

    @Test
    public void testInsert() {
        CrDao dao = getDao();
        Long id = dao.save(cr);
        Cr cr = dao.getById(id);
        assertEquals(name, cr.getCrName());
        assertNotNull(cr.getCrFirstName());
        assertNotNull(cr.getCrLastName());
        assertNotNull(cr.getCrPosition());
    }

    @Test
    public void testUpdate() {
        CrDao dao = getDao();
        Long id = 1L;
        String name = "Denis";
        Long compId = 100L;
        Cr cr = dao.getById(id);
        assertEquals("Bill", cr.getCrName());
        cr.setCrName(name);
        cr.setCompanyId(compId);
        dao.save(cr);
        Cr newCr = dao.getById(id);
        assertEquals(name, newCr.getCrName());
        assertEquals(compId, newCr.getCompanyId());
        assertNotNull(newCr.getCrFirstName());
        assertNotNull(newCr.getCrLastName());
    }

    @Test
    public void testDelete() {
        CrDao dao = getDao();
        int size = dao.getAll().size();
        dao.delete(2L);
        assertEquals(size - 1, dao.getAll().size());
    }

    @Test
    public void testExistentEmail(){
        CrDao dao = getDao();
        assertEquals("Bill", dao.existentEmail("email1"));
    }

    @Test
    public void testNonExistentEmail(){
        CrDao dao = getDao();
        assertNull(dao.existentEmail("!!!"));
    }

    //Abnormal tests

    @Test
    public void testGetByIdNonExistent() {
        CrDao dao = getDao();
        assertNull(dao.getById(-1L));
    }

    @Test
    public void testGetByNameNonExistent() {
        CrDao dao = getDao();
        assertNull(dao.getByName("!!!"));
    }

    @Test(expected = DaoException.class)
    public void testInsertExistentUserException() {
        CrDao dao = getDao();
        String newName = "Bill";
        cr.setCrName(newName);
        assertNull(dao.save(cr));
    }

    @Test
    public void testInsertExistentUserDbSize() {
        CrDao dao = getDao();
        String newName = "Bill";
        cr.setCrName(newName);
        int size = dao.getAll().size();
        try {
            assertNull(dao.save(cr));
        } catch (DaoException e) {
            assertEquals(size, dao.getAll().size());
        }
    }

    @Test
    public void testDeleteNonExistentUser(){
        CrDao dao = getDao();
        assertFalse(dao.delete(-1L));
    }



    private CrDao getDao(){
        return new PostgresCrDao(new JdbcTemplate(db.getTestDatabase()), tableName);
    }

}
