package com.gdc.aerodev.repository.postgresql;

import com.gdc.aerodev.model.Cr;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface CrRepository extends Repository<Cr, Long> {
    List<Cr> findAllByCompanyId(Long companyId);
    List<Cr> findAll();
    Cr findByCrId(Long crId);
    Cr findByCrName(String crName);
    Cr save(Cr cr);
    void deleteByCrId(Long crId);
}
