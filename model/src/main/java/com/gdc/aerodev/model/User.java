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
    /**
     * @param userName used as <b>nickname</b> to get access to user's profile
     */
    private String userName;
    private String userPassword;
    private String userEmail;
    /**
     * @param userLevel describes summary user's competence
     */
    private short userLevel;
    private String userFirstName;
    private String userLastName;
    private String userBiography;
    /**
     * @param userRating counts according to his activity.
     */
    private int userRating;

    public User() {
    }

    public User(Long userId, String userName, String userPassword, String userEmail, short userLevel) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
        this.userLevel = userLevel;
        this.userFirstName="";
        this.userLastName="";
        this.userBiography="";
    }

    public User(String userName, String userPassword, String userEmail) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
        this.userFirstName="";
        this.userLastName="";
        this.userBiography="";
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

    public String getUserFirstName() {
        return userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public String getUserBiography() {
        return userBiography;
    }

    public int getUserRating() {
        return userRating;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserLevel(short userLevel) {
        this.userLevel = userLevel;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public void setUserBiography(String userBiography) {
        this.userBiography = userBiography;
    }

    public void setUserRating(int userRating) {
        this.userRating = userRating;
    }
}
