package com.gdc.aerodev.repository.postgresql;

import com.gdc.aerodev.model.Cr;
import com.gdc.aerodev.model.Offer;
import com.gdc.aerodev.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findAllByOfferedUser(User user);
    List<Offer> findAllByOfferedCr(Cr companyRepresentative);
}