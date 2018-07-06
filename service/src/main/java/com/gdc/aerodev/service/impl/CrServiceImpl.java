package com.gdc.aerodev.service.impl;

import com.gdc.aerodev.dao.postgres.PostgresCrDao;
import com.gdc.aerodev.model.Cr;
import com.gdc.aerodev.service.CrService;
import com.gdc.aerodev.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrServiceImpl implements CrService {
    private PostgresCrDao dao;

    @Autowired
    public CrServiceImpl(PostgresCrDao dao) {
        this.dao = dao;
    }

    @Override
    public Cr get(Long id) {
        if (id > 0 || id != null) {
            return dao.getById(id);
        }
        throw new ServiceException("Cannot get company representative's data with id: " + id + ".");
    }

    @Override
    public Cr get(String name) {
        if (!name.equals("") || name != null) {
            return dao.getByName(name);
        }
        throw new ServiceException("Cannot get company representative's data with name: '" + name + "'.");
    }

    @Override
    public Long save(Cr cr) {
        if (cr != null) {
            return dao.save(cr);
        }
        throw new ServiceException("Cannot create company representative's data.");
    }

    @Override
    public List<Cr> getAll() {
        return dao.getAll();
    }
}
