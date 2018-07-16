package com.gdc.aerodev.service.impl;

import com.gdc.aerodev.model.Cr;
import com.gdc.aerodev.repository.postgresql.CrRepository;
import com.gdc.aerodev.service.CrService;
import com.gdc.aerodev.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrServiceImpl implements CrService {
    private CrRepository repository;

    @Autowired
    public CrServiceImpl(CrRepository repository) {
        this.repository = repository;
    }

    @Override
    public Cr get(Long id) {
        if (id > 0 || id != null) {
            return repository.findById(id).get();
        }
        throw new ServiceException("Cannot get company representative's data with id: " + id + ".");
    }

    @Override
    public Cr get(String name) {
        if (!name.equals("") || name != null) {
            return repository.findByCrName(name);
        }
        throw new ServiceException("Cannot get company representative's data with name: '" + name + "'.");
    }

    @Override
    public Long save(Cr cr) {
        if (cr != null) {
            return repository.save(cr).getCrId();
        }
        throw new ServiceException("Cannot create company representative's data.");
    }

    @Override
    public List<Cr> getAll() {
        return repository.findAll();
    }
}
