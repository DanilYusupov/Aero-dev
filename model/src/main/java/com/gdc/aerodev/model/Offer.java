package com.gdc.aerodev.model;

import javax.persistence.*;

/**
 * This is offer between {@code User} and {@code Cr}. Offer initiator can be only {@code Cr}. {@code Offer} has
 * links: {@param offeredUserId} to {@code User} and {@param offeredCrId} to {@code Cr}.
 *
 * @author Yusupov Danil
 * @see User
 * @see Cr
 */
//@Entity
//@Table(name = "offers")
public class Offer {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "off_id")
    private Long offerId;

    /**
     * Refers to {@code userId} of {@code User}
     */
//    @Column(name = "off_usr_id")
//    @ManyToOne
    private Long offeredUserId;

    /**
     * Refers to {@code crId} of {@code Cr}
     */
//    @Column(name = "off_cr_id")
//    @ManyToOne
    private Long offeredCrId;
//    @Column(name = "off_description")
    private String offerDescription;
    private Status status;

    public enum Status{
        INITIATED, CONFIRMED, CANCELED, EXPIRED
    }

    public Offer() {
    }

    public Offer(Long offerId, Long offeredUserId, Long offeredCrId, String offerDescription, Status status) {
        this.offerId = offerId;
        this.offeredUserId = offeredUserId;
        this.offeredCrId = offeredCrId;
        this.offerDescription = offerDescription;
        this.status = status;
    }

    public Offer(Long offeredUserId, Long offeredCrId, String offerDescription, Status status) {
        this.offeredUserId = offeredUserId;
        this.offeredCrId = offeredCrId;
        this.offerDescription = offerDescription;
        this.status = status;
    }

    public Long getOfferId() {
        return offerId;
    }

    public Long getOfferedUserId() {
        return offeredUserId;
    }

    public Long getOfferedCrId() {
        return offeredCrId;
    }

    public String getOfferDescription() {
        return offerDescription;
    }

    public Offer setOfferId(Long offerId) {
        this.offerId = offerId;
        return this;
    }

    public Offer setOfferedUserId(Long offeredUserId) {
        this.offeredUserId = offeredUserId;
        return this;
    }

    public Offer setOfferedCrId(Long offeredCrId) {
        this.offeredCrId = offeredCrId;
        return this;
    }

    public Offer setOfferDescription(String offerDescription) {
        this.offerDescription = offerDescription;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public Offer setStatus(Status status) {
        this.status = status;
        return this;
    }
}
