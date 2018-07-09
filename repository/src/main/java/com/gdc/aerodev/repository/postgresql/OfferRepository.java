package com.gdc.aerodev.repository.postgresql;

import com.gdc.aerodev.model.Offer;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface OfferRepository extends Repository<Offer, Long> {
    List<Offer> findAllByOfferedUserId(Long userId);
    List<Offer> findAllByOfferedCrId(Long crId);
    Offer findByOfferId(Long offerId);
    Offer save(Offer offer);
    void deleteByOfferId(Long offerId);
}
