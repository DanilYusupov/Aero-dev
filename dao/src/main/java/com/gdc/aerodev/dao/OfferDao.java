package com.gdc.aerodev.dao;

import com.gdc.aerodev.model.Offer;

import java.util.List;

public interface OfferDao extends GenericDao<Offer, Long> {
    List<Offer> getByUserId(Long userId);
}
