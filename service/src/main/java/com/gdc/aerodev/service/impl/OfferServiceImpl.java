package com.gdc.aerodev.service.impl;

import com.gdc.aerodev.dao.OfferDao;
import com.gdc.aerodev.model.Offer;
import com.gdc.aerodev.service.OfferService;
import com.gdc.aerodev.service.exception.ServiceException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {

    private OfferDao offerDao;

    public OfferServiceImpl(OfferDao offerDao) {
        this.offerDao = offerDao;
    }

    @Override
    public Offer getByOfferId(Long id) {
        return (id != null || id > 0) ? offerDao.getById(id) : null;
    }

    @Override
    public Long createOffer(Long userId, Long crId, String description) {
        if (userId <= 0 || userId == null || crId <= 0 || crId == null || description == null){
            return null;
        }
        return offerDao.save(new Offer(userId, crId, description, Offer.Status.INITIATED));
    }

    @Override
    public Long updateStatus(Long offerId, String status) {
        Offer.Status statusCheck;
        try{
            statusCheck = Offer.Status.valueOf(status);
            Offer offer = offerDao.getById(offerId);
            offer.setStatus(statusCheck);
            return offerDao.save(offer);
        } catch (IllegalArgumentException e){
            throw new ServiceException("Wrong status: " + status, e);
        }
    }

    @Override
    public boolean deleteOffer(Long offerId) {
        return offerDao.delete(offerId);
    }

    @Override
    public List<Offer> getByUserId(Long userId) {
        // TODO: 05.07.2018 realize
        return offerDao.getByUserId(userId);
    }

    @Override
    public List<Offer> getByCrId(Long crId) {
        // TODO: 05.07.2018 realize
        return null;
    }
}
