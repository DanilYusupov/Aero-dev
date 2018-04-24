package com.gdc.aerodev.dao;

import com.gdc.aerodev.model.User;
import java.util.List;

/**
 * DAO interface for working with {@code User} entity
 *
 * @see com.gdc.aerodev.model.User
 * @author Yusupov Danil
 */
public interface UserDao extends GenericDao<User, Long> {

    /**
     * Checks inserted email on existence in database
     * @param userEmail email to check
     * @return (0) {@param userName} of {@code User} with existent email
     *         (1) {@code null} if there is no such email
     */
    String existentEmail(String userEmail);

    /**
     * Counts all number of entities in table with simple query which returns {@code int}
     * @return number of entities in table
     */
    int count();

    /**
     * @return three high rated {@code User}'s based on their rating.
     */
    List<User> getTopThree();
}
