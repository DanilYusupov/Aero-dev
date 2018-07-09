package com.gdc.aerodev.repository.postgresql;

import com.gdc.aerodev.model.Company;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface CompanyRepository extends Repository<Company, Long> {
    List<Company> findAll();
    Company findByCompId(Long companyId);
    Company findByCompName(String companyName);
    Company save(Company company);
    void deleteByCompId(Long companyId);
}
