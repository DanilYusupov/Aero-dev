package com.gdc.aerodev.model;

import javax.persistence.*;

/**
 * This entity's goal is to contain {@code User}'s profile image.
 * Image data stores as byte array in {@code avatarData} field.
 * To bind {@code User} with his {@code Avatar} there is {@code avatarOwner} parameter.
 *
 * @author Yusupov Danil
 * @see com.gdc.aerodev.model.User
 */
//@Entity
//@Table(name = "avatars")
public class Avatar {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "av_id")
    private Long avatarId;

    /**
     * {@code userId} as {@code FOREIGN KEY} and {@code PRIMARY KEY}
     */
//    @Column(name = "av_owner")
//    @ManyToOne
    private Long avatarOwner;

    /**
     * Image as array of bytes
     */
//    @Column(name = "av_data")
    private byte[] avatarData;

    /**
     * MIME type of avatar image
     */
//    @Column(name = "av_type")
    private String contentType;

    public Avatar() {
    }

    public Avatar(Long avatarOwner, byte[] avatarData, String contentType) {
        this.avatarOwner = avatarOwner;
        this.avatarData = avatarData;
        this.contentType = contentType;
    }

    public Avatar(Long avatarId, Long avatarOwner, byte[] avatarData, String contentType) {
        this.avatarId = avatarId;
        this.avatarOwner = avatarOwner;
        this.avatarData = avatarData;
        this.contentType = contentType;
    }

    public Long getAvatarId() {
        return avatarId;
    }

    public Long getAvatarOwner() {
        return avatarOwner;
    }

    public byte[] getAvatarData() {
        return avatarData;
    }

    public String getContentType() {
        return contentType;
    }

    public void setAvatarId(Long avatarId) {
        this.avatarId = avatarId;
    }

    public void setAvatarOwner(Long avatarOwner) {
        this.avatarOwner = avatarOwner;
    }

    public void setAvatarData(byte[] avatarData) {
        this.avatarData = avatarData;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
