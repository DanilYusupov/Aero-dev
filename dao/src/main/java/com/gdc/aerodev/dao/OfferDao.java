package com.gdc.aerodev.dao;

import com.gdc.aerodev.model.Cr;
import com.gdc.aerodev.model.Offer;
import com.gdc.aerodev.model.User;

import java.util.List;

public interface OfferDao extends GenericDao<Offer, Long> {
    List<Offer> getByUserId(Long userId);

    Cr getCrFromOffer(Long offerId);

    User getUserFromOffer(Long offerId);
}