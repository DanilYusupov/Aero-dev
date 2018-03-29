package com.gdc.aerodev.model;

/**
 * This class describes engineer in web-portal. Every newcomers can register in web-service as {@code User}.
 * Strictly {@code User} can create {@code Project} and in future add files there. Bind between {@code User}
 * and {@code Project} exists in special SQL tables.
 *
 * @author Yusupov Danil
 */
public class User {

    private Long userId;
    private String userName;
    private String userPassword;
    private String userEmail;
    private short userLevel;

    public User() {
    }

    public User(Long userId, String userName, String userPassword, String userEmail, short userLevel) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
        this.userLevel = userLevel;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public short getUserLevel() {
        return userLevel;
    }
}
