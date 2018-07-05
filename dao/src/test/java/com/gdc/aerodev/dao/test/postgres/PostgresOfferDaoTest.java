package com.gdc.aerodev.dao.test.postgres;

import com.gdc.aerodev.dao.OfferDao;
import com.gdc.aerodev.dao.exception.DaoException;
import com.gdc.aerodev.dao.postgres.PostgresOfferDao;
import com.gdc.aerodev.model.Offer;
import com.opentable.db.postgres.embedded.FlywayPreparer;
import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.PreparedDbRule;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.Assert.*;

public class PostgresOfferDaoTest {
    /**
     * Name of table according to classpath:/user/V1__Create_test_table.sql
     */
    private String tableName = "offers_test";
    private Long userId = 49L;
    private Long crId = 2L;
    private String text = "Some text...";
    private Offer offer = new Offer(userId, crId, text);

    @Rule
    public PreparedDbRule db = EmbeddedPostgresRules.preparedDatabase(FlywayPreparer.forClasspathLocation("offers"));

    //Standard tests

    @Test
    public void testGetById() {
        OfferDao dao = getDao();
        Offer offer = dao.getById(1L);
        assertEquals(Long.valueOf(21), offer.getOfferedUserId());
    }

    @Test
    public void testGetAll() {
        OfferDao dao = getDao();
        List<Offer> list = dao.getAll();
        assertEquals(3, list.size());
    }

    @Test
    public void testInsert() {
        OfferDao dao = getDao();
        Long id = dao.save(offer);
        Offer offer = dao.getById(id);
        assertEquals(userId, offer.getOfferedUserId());
        assertNotNull(offer.getOfferedCrId());
    }

    @Test
    public void testUpdate() {
        OfferDao dao = getDao();
        Long id = 1L;
        String text = "Denis";
        Offer offer = dao.getById(id);
        assertEquals(Long.valueOf(21L), offer.getOfferedUserId());
        offer.setOfferDescription(text);
        dao.save(offer);
        Offer newOffer = dao.getById(id);
        assertEquals(text, newOffer.getOfferDescription());
        assertNotNull(newOffer.getOfferedUserId());
    }

    @Test
    public void testDelete() {
        OfferDao dao = getDao();
        int size = dao.getAll().size();
        dao.delete(2L);
        assertEquals(size - 1, dao.getAll().size());
    }

    //Abnormal tests

    @Test
    public void testGetByIdNonExistent() {
        OfferDao dao = getDao();
        assertNull(dao.getById(-1L));
    }

    @Test
    public void testInsertExistentUserDbSize() {
        OfferDao dao = getDao();
        offer.setOfferId(1L);
        int size = dao.getAll().size();
        try {
            dao.save(offer);
        } catch (DaoException e) {
            assertEquals(size, dao.getAll().size());
        }
    }

    @Test
    public void testDeleteNonExistentUser() {
        OfferDao dao = getDao();
        assertFalse(dao.delete(-1L));
    }

    private OfferDao getDao() {
        return new PostgresOfferDao(new JdbcTemplate(db.getTestDatabase()), tableName);
    }

}
