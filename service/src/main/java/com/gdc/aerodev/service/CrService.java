package com.gdc.aerodev.service;

import com.gdc.aerodev.model.Cr;
import com.gdc.aerodev.service.logging.LoggingService;

import java.util.List;

public interface CrService extends LoggingService {
    Cr get(Long id);
    Cr get(String name);
    Long save(Cr cr);
    List<Cr> getAll();
}
