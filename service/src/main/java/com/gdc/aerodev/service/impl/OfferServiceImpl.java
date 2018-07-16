package com.gdc.aerodev.service.impl;

import com.gdc.aerodev.model.Cr;
import com.gdc.aerodev.model.Offer;
import com.gdc.aerodev.model.User;
import com.gdc.aerodev.repository.postgresql.CrRepository;
import com.gdc.aerodev.repository.postgresql.OfferRepository;
import com.gdc.aerodev.repository.postgresql.UserRepository;
import com.gdc.aerodev.service.OfferService;
import com.gdc.aerodev.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {

    private OfferRepository repository;
    private UserRepository userRepository;
    private CrRepository crRepository;

    @Autowired
    public OfferServiceImpl(OfferRepository repository, UserRepository userRepository, CrRepository crRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.crRepository = crRepository;
    }

    @Override
    public Offer getByOfferId(Long id) {
        return (id != null || id > 0) ? repository.findById(id).get() : null;
    }

    @Override
    public Long createOffer(Long userId, Long crId, String description) {
        if (userId <= 0 || userId == null || crId <= 0 || crId == null || description == null){
            return null;
        }
        User user = userRepository.findByUserId(userId);
        Cr cr = crRepository.findById(crId).get();
        if (user == null){
            throw new ServiceException("There is no engineer with id:" + userId + " for new offer.");
        }
        return repository.save(new Offer(user, cr, description, Offer.Status.INITIATED)).getOfferId();
    }

    @Override
    public Long updateStatus(Long offerId, String status) {
        Offer.Status statusCheck;
        try{
            statusCheck = Offer.Status.valueOf(status);
            Offer offer = repository.findById(offerId).get();
            offer.setStatus(statusCheck);
            return repository.save(offer).getOfferId();
        } catch (IllegalArgumentException e){
            throw new ServiceException("Wrong status: " + status, e);
        }
    }

    @Override
    public void deleteOffer(Long offerId) {
        repository.deleteById(offerId);
    }

    @Override
    public List<Offer> getByUserId(Long userId) {
        User user = userRepository.findByUserId(userId);
        return repository.findAllByOfferedUser(user);
    }

    @Override
    public List<Offer> getByCrId(Long crId) {
        Cr cr = crRepository.findById(crId).get();
        return repository.findAllByOfferedCr(cr);
    }
}