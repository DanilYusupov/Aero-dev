package com.gdc.aerodev.repository.postgresql.test;

import com.gdc.aerodev.model.Company;
import com.gdc.aerodev.repository.postgresql.CompanyRepository;
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

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@AutoConfigureEmbeddedDatabase
@FlywayTest
@DataJpaTest
public class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository repository;

    private String compName = "ЦАГИ им. Н. Е. Жуковского";
    private String compWebsite = "http://www.tsagi.ru/";
    private String compLogoUrl = "http://stroyneryd-beton.ru/assets/images/clients/index/1f38f16b1b1a829940be0d83163f04b8.jpg";
    private int count = 3;

    private String newCompName = "Cool company";
    private String newCompWebSite = "our-site.com";
    private String newCompanyLogoUrl = "logo.url";

    @Test
    public void createCompanyTest(){
        createCompany();
        assertEquals(count + 1, repository.findAll().size());
    }

    @Test
    public void updateCompanyTest(){
        Company company = createCompany();
        company.setCompLogoUrl(compLogoUrl);
        company = repository.save(company);
        assertEquals(compLogoUrl, company.getCompLogoUrl());
    }

    @Test
    public void getByIdTest(){
        Company company = createCompany();
        Company fetched = repository.findById(company.getCompId()).get();
        assertEquals(company, fetched);
    }

    @Test
    public void getByNameTest(){
        Company company = createCompany();
        Company fetched = repository.findByCompName(company.getCompName());
    }

    @Test
    public void deleteTest(){
        Company company = createCompany();
        assertEquals(count + 1, repository.findAll().size());
        repository.delete(company);
        assertEquals(count, repository.findAll().size());
        company = createCompany();
        repository.deleteById(company.getCompId());
        assertEquals(count, repository.findAll().size());
    }

    //Abnormal tests

    @Test
    public void getFakeIdTest(){
        assertEquals(Optional.empty(), repository.findById(0L));
    }

    @Test
    public void getByFakeNameTest(){
        assertNull(repository.findByCompName(""));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveExistentNameTest(){
        Company company = new Company(compName, compWebsite, compLogoUrl);
        repository.save(company);
    }

    private Company createCompany(){
        Company company = new Company(newCompName, newCompWebSite, newCompanyLogoUrl);
        return repository.save(company);
    }

}
