package com.gdc.aerodev.model;

/**
 * This is offer between {@code User} and {@code Cr}. Offer initiator can be only {@code Cr}. {@code Offer} has
 * links: {@param offeredUserId} to {@code User} and {@param offeredCrId} to {@code Cr}.
 *
 * @author Yusupov Danil
 * @see User
 * @see Cr
 */
public class Offer {
    private Long offerId;

    /**
     * Refers to {@code userId} of {@code User}
     */
    private Long offeredUserId;

    /**
     * Refers to {@code crId} of {@code Cr}
     */
    private Long offeredCrId;
    private String offerDescription;

    public Offer() {
    }

    public Offer(Long offerId, Long offeredUserId, Long offeredCrId, String offerDescription) {
        this.offerId = offerId;
        this.offeredUserId = offeredUserId;
        this.offeredCrId = offeredCrId;
        this.offerDescription = offerDescription;
    }

    public Offer(Long offeredUserId, Long offeredCrId, String offerDescription) {
        this.offeredUserId = offeredUserId;
        this.offeredCrId = offeredCrId;
        this.offerDescription = offerDescription;
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
}
