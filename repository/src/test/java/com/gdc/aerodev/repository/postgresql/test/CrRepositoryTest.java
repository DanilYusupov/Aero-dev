package com.gdc.aerodev.repository.postgresql.test;

import com.gdc.aerodev.model.Company;
import com.gdc.aerodev.model.Cr;
import com.gdc.aerodev.repository.postgresql.CompanyRepository;
import com.gdc.aerodev.repository.postgresql.CrRepository;
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

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@AutoConfigureEmbeddedDatabase
@FlywayTest
@DataJpaTest
public class CrRepositoryTest {

    @Autowired
    private CrRepository repository;

    @Autowired
    private CompanyRepository companyRepository;

    private Long crId = 1L;
    private String crName = "cr1";
    private String crPosition = "Senior engineer";
    private Long compId = 1L;
    private byte count = 3;

    private String name = "new Cr";

    @Test
    public void createCrTest(){
        Cr cr = createCr();
        assertEquals(count + 1, repository.findAll().size());
    }

    @Test
    public void updateTest(){
        String newName = "NEW";
        Cr cr = createCr();
        cr.setCrName(newName);
        repository.save(cr);
        assertEquals(count + 1, repository.findAll().size());
        assertNotNull(repository.findByCrName(newName));
    }

    @Test
    public void getByIdTest(){
        assertEquals(crName, repository.findById(crId).get().getCrName());
    }

    @Test
    public void getByName(){
        assertEquals(crPosition, repository.findByCrName(crName).getCrPosition());
    }

    @Test
    public void deleteTest(){
        Cr cr = createCr();
        repository.delete(cr);
        assertEquals(count, repository.findAll().size());
        cr = createCr();
        repository.deleteById(cr.getCrId());
        assertEquals(count, repository.findAll().size());
    }

    @Test
    public void cascadeDeleteTest(){
        int num = 3;
        Company company = companyRepository.findById(compId).get();
        for (int i = 0; i < num; i++) {
            repository.save(new Cr(name + i, "pass", "e" + i, company, "f", "s", "p"));
        }
        companyRepository.deleteById(compId);
        assertEquals(count - 1, repository.findAll().size());
    }

    //Abnormal tests

    @Test
    public void getFakeIdTest(){
        assertEquals(Optional.empty(), repository.findById(0L));
    }

    @Test
    public void getFakeNameTest(){
        assertNull(repository.findByCrName(""));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveExistentNameTest(){
        Company company = companyRepository.findById(compId).get();
        Cr cr = new Cr(crName, "r", "ew", company, "efew", "fewf", "fwef");
        repository.save(cr);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveNullCompanyTest(){
        Cr cr = new Cr(name, "r", "ew", null, "efew", "fewf", "fwef");
        repository.save(cr);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void deleteNonExistentCrTest(){
        repository.deleteById(0L);
    }

    private Cr createCr(){
        Company company = companyRepository.findById(compId).get();
        Cr cr = new Cr(name, "pass", "email", company, "first", "last", "position");
        return repository.save(cr);
    }

}
