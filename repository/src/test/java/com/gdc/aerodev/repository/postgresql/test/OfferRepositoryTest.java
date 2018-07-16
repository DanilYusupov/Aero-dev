package com.gdc.aerodev.repository.postgresql.test;

import com.gdc.aerodev.model.Cr;
import com.gdc.aerodev.model.Offer;
import com.gdc.aerodev.model.User;
import com.gdc.aerodev.repository.postgresql.CrRepository;
import com.gdc.aerodev.repository.postgresql.OfferRepository;
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
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@AutoConfigureEmbeddedDatabase
@FlywayTest
@DataJpaTest
public class OfferRepositoryTest {

    @Autowired
    private OfferRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CrRepository crRepository;

    private Long userId = 1L;
    private Long crId = 1L;

    private String description = "Offer #223190. Dear Aigul, we glad to...";
    private Offer.Status status = Offer.Status.INITIATED;

    @Test
    public void createOfferTest() {
        createOffer();
        assertEquals(1, repository.findAll().size());
    }

    @Test
    public void updateTest() {
        Offer.Status upd = Offer.Status.CONFIRMED;
        Offer offer = createOffer();
        offer.setStatus(upd);
        repository.save(offer);
        assertEquals(upd, repository.findById(offer.getOfferId()).get().getStatus());
    }

    @Test
    public void getByIdTest() {
        Offer offer = createOffer();
        assertEquals(offer, repository.findById(offer.getOfferId()).get());
        assertTrue(repository.findById(offer.getOfferId()).isPresent());
    }

    @Test
    public void getByUser() {
        User user = userRepository.findByUserId(userId);
        Offer offer = createOffer();
        List<Offer> batch = repository.findAllByOfferedUser(user);
        assertEquals(1, batch.size());
        assertEquals(offer, batch.get(0));
    }

    @Test
    public void getByCrTest() {
        Cr cr = crRepository.findById(crId).get();
        Offer offer = createOffer();
        List<Offer> batch = repository.findAllByOfferedCr(cr);
        assertEquals(1, batch.size());
        assertEquals(offer, batch.get(0));
    }

    @Test
    public void deleteTest() {
        Offer offer = createOffer();
        assertEquals(1, repository.findAll().size());
        repository.delete(offer);
        assertEquals(0, repository.findAll().size());
        offer = createOffer();
        repository.deleteById(offer.getOfferId());
        assertEquals(0, repository.findAll().size());
    }

    //Abnormal tests

    @Test
    public void getFakeIdTest() {
        assertFalse(repository.findById(0L).isPresent());
    }

    @Test
    public void getFakeUserTest() {
        assertEquals(Collections.EMPTY_LIST, repository.findAllByOfferedUser(null));
    }

    @Test
    public void getFakeCrTest() {
        assertEquals(Collections.EMPTY_LIST, repository.findAllByOfferedCr(null));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveNullUserTest() {
        Cr cr = crRepository.findById(crId).get();
        Offer offer = new Offer(null, cr, description, status);
        repository.save(offer);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveNullCrTest() {
        User user = userRepository.findByUserId(userId);
        Offer offer = new Offer(user, null, description, status);
        repository.save(offer);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void deleteFakeOffer(){
        Offer offer = new Offer(null, null, description, status);
        repository.delete(offer);
    }

    private Offer createOffer() {
        User user = userRepository.findByUserId(userId);
        Cr cr = crRepository.findById(crId).get();
        Offer offer = new Offer(user, cr, description, status);
        return repository.save(offer);
    }

}
