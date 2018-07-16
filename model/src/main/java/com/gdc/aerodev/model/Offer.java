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
@Entity
@Table(schema = "aero", name = "offers")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "off_id")
    private Long offerId;

    /**
     * Refers to {@code userId} of {@code User}
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usr_id", foreignKey = @ForeignKey(name = "off_usr_id"))
    private User offeredUser;

    /**
     * Refers to {@code crId} of {@code Cr}
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cr_id", foreignKey = @ForeignKey(name = "off_cr_id"))
    private Cr offeredCr;

    @Column(name = "off_description")
    private String offerDescription;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        INITIATED, CONFIRMED, CANCELED, EXPIRED
    }

    public Offer() {
    }

    public Offer(User offeredUser, Cr offeredCr, String offerDescription, Status status) {
        this.offeredUser = offeredUser;
        this.offeredCr = offeredCr;
        this.offerDescription = offerDescription;
        this.status = status;
    }

    public Long getOfferId() {
        return offerId;
    }

    public String getOfferDescription() {
        return offerDescription;
    }

    public Offer setOfferId(Long offerId) {
        this.offerId = offerId;
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

    public User getOfferedUser() {
        return offeredUser;
    }

    public Offer setOfferedUser(User offeredUser) {
        this.offeredUser = offeredUser;
        return this;
    }

    public Cr getOfferedCr() {
        return offeredCr;
    }

    public Offer setOfferedCr(Cr offeredCr) {
        this.offeredCr = offeredCr;
        return this;
    }
}
