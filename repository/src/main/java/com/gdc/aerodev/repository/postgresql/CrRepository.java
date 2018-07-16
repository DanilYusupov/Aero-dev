package com.gdc.aerodev.repository.postgresql;

import com.gdc.aerodev.model.Company;
import com.gdc.aerodev.model.Cr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrRepository extends JpaRepository<Cr, Long> {
    List<Cr> findAllByCompany(Company company);
    Cr findByCrName(String name);
}
