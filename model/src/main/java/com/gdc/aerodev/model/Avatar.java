package com.gdc.aerodev.model;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;

/**
 * This entity's goal is to contain {@code User}'s profile image.
 * Image data stores as byte array in {@code avatarData} field.
 * To bind {@code User} with his {@code Avatar} there is {@code avatarOwner} parameter.
 *
 * @author Yusupov Danil
 * @see com.gdc.aerodev.model.User
 */
@Entity
@Table(schema = "aero", name = "avatars")
public class Avatar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "av_id")
    private Long avatarId;

    /**
     * {@code userId} as {@code FOREIGN KEY} and {@code PRIMARY KEY}
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usr_id", foreignKey = @ForeignKey(name = "av_fk"))
    @NotNull
    private User user;

    /**
     * Image as array of bytes
     */
    @Column(name = "av_data")
    @NotNull
    private byte[] avatarData;

    /**
     * MIME type of avatar image
     */
    @Column(name = "av_type")
    private String contentType;

    public Avatar() {
    }

    public Avatar(byte[] avatarData, String contentType) {
        this.avatarData = avatarData;
        this.contentType = contentType;
    }

    public Avatar(Long avatarId, byte[] avatarData, String contentType) {
        this.avatarId = avatarId;
        this.avatarData = avatarData;
        this.contentType = contentType;
    }

    public Long getAvatarId() {
        return avatarId;
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

    public void setAvatarData(byte[] avatarData) {
        this.avatarData = avatarData;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public User getUser() {
        return user;
    }

    public Avatar setUser(User user) {
        this.user = user;
        return this;
    }
}
