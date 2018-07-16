package com.gdc.aerodev.service;

import com.gdc.aerodev.model.Offer;
import com.gdc.aerodev.service.logging.LoggingService;

import java.util.List;

public interface OfferService extends LoggingService {

    Offer getByOfferId(Long id);

    Long createOffer(Long userId, Long crId, String description);

    Long updateStatus(Long offerId, String status);

    boolean deleteOffer(Long offerId);

    List<Offer> getByUserId(Long userId);

    List<Offer> getByCrId(Long crId);

}
