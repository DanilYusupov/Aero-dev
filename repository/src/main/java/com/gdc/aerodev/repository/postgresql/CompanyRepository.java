package com.gdc.aerodev.repository.postgresql;

import com.gdc.aerodev.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findByCompName(String name);
}
