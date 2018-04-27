package com.gdc.aerodev.service.impl;

import com.gdc.aerodev.dao.UserDao;
import com.gdc.aerodev.dao.exception.DaoException;
import com.gdc.aerodev.dao.postgres.PostgresUserDao;
import com.gdc.aerodev.model.User;
import com.gdc.aerodev.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of service for managing users in database
 *
 * @author Yusupov Danil
 * @see UserService
 * @see UserDao
 * @see User
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserDao dao;

    public UserServiceImpl(PostgresUserDao dao) {
        this.dao = dao;
    }

    @Override
    public List<User> getTopThree() {
        return dao.getTopThree();
    }

    @Override
    public void updateInfo(Long id, String firstName, String lastName, String biography, String userCountry, String userCity) {
        User user = dao.getById(id);
        user.setUserFirstName(firstName);
        user.setUserLastName(lastName);
        user.setUserBiography(biography);
        user.setUserCountry(userCountry);
        user.setUserCity(userCity);
        if (dao.save(user) == null) {
            log.error("Nothing to update for user '" + getUser(id).getUserName() + "'.");
        } else {
            log.info("Updated info for user '" + getUser(id).getUserName() + "'.");
        }
    }

    @Override
    public Long createUser(String userName, String userPassword, String userEmail, boolean isMale) {
        if (userName.equals("") || userPassword.equals("") || userEmail.equals("")) {
            return null;
        }
        if (isExistentName(userName)) {
            log.error("User with name '" + userName + "' is already exists.");
            return null;
        }
        String emailOwner = dao.existentEmail(userEmail);
        if (emailOwner != null) {
            log.error("This email is already used by '" + emailOwner + "'.");
            return null;
        }
        try {
            Long id = dao.save(new User(userName, userPassword, userEmail, isMale));
            log.info("Successful created user '" + userName + "' with id " + id + ".");
            return id;
        } catch (DaoException e) {
            return null;
        }
    }

    @Override
    public Long updateUser(Long userId, String userName, String userPassword, String userEmail, short userLevel) {
        User user = dao.getById(userId);
        if (!userName.equals("")) {
            if (isExistentName(userName)) {
                log.error("User with name '" + userName + "' is already exists.");
                return null;
            }
            user.setUserName(userName);
        } else if (userPassword.equals("") && userEmail.equals("") && user.getUserLevel() == userLevel) {
            return null;
        }
        if (!userPassword.equals("")) {
            user.setUserPassword(userPassword);
        }
        if (!userEmail.equals("")) {
            String emailOwner = dao.existentEmail(userEmail);
            if (emailOwner != null) {
                log.error("This email is already used by '" + emailOwner + "'.");
                return null;
            }
            user.setUserEmail(userEmail);
        }
        user.setUserLevel(userLevel);
        try {
            return dao.save(user);
        } catch (DaoException e) {
            return null;
        }
    }

    @Override
    public User getUser(String name) {
        return dao.getByName(name);
    }

    @Override
    public User getUser(Long id) {
        return dao.getById(id);
    }

    @Override
    public int countUsers() {
        return dao.count();
    }

    /**
     * Check name existence for avoid name duplicating
     *
     * @param userName name for search
     * @return (0) {@code true} if there is already got user with this name <br>
     * (1) {@code false} if there is no user with matching name
     */
    private boolean isExistentName(String userName) {
        return dao.getByName(userName) != null;
    }

}
