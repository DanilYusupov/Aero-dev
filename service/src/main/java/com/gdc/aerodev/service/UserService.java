package com.gdc.aerodev.service;

import com.gdc.aerodev.model.User;
import com.gdc.aerodev.service.logging.LoggingService;

import java.util.List;

/**
 * Generic interface of service, which makes manipulations with {@code User} entity.
 *
 * @author Yusupov Danil
 * @see com.gdc.aerodev.model.User
 */
public interface UserService extends LoggingService {
    /**
     * Inserts {@code User} into database configured by input parameters.
     *
     * @param userName     name of new {@code User}
     * @param userPassword password of {@code User}
     * @param userEmail    email of current {@code Project}
     * @param isMale       gender indicator
     * @return (0) {@param userId} of inserted {@code User} <br>
     * (1) or {@code null} in cause of problems
     */
    Long createUser(String userName, String userPassword, String userEmail, boolean isMale);

    /**
     * Updates existent {@code User} chosen by {@param userId} with input parameters. If there is no need to
     * change some parameter, it should be left as empty ones.
     *
     * @param userId       ID of updating {@code User}
     * @param userName     new name of updating {@code User}
     * @param userPassword new password of updating {@code User}
     * @param userEmail    new email for {@code User}
     * @param userLevel    new user level
     * @return (0) {@param userId} of updated {@code User} <br>
     * (1) or {@code null} in cause of problems
     */
    Long updateUser(Long userId, String userName, String userPassword, String userEmail, short userLevel);

    /**
     * Encapsulates same method in {@code UserDao}
     *
     * @param name user's nickname
     * @return (0) {@code User} or <br>
     * (1) {@code null} if there is no such user
     */
    User getUser(String name);

    /**
     * Encapsulates same method in {@code UserDao}
     *
     * @param id user's id
     * @return (0) {@code User} or <br>
     * (1) {@code null} if there is no such user
     */
    User getUser(Long id);

    /**
     * Encapsulates same method in {@code UserDao}
     *
     * @return list of top three users with biggest rating
     */
    List<User> getTopThree();

    /**
     * Updates user's profile information.
     *
     * @param id          of target user
     * @param firstName   of user
     * @param lastName    of user
     * @param biography   of user
     * @param userCountry user's country
     * @param userCity    user's city
     */
    void updateInfo(Long id, String firstName, String lastName, String biography, String userCountry, String userCity);

    /**
     * Counts all users in DB...
     *
     * @return number of users
     */
    long countUsers();
}
