package com.gdc.aerodev.service.impl;

import com.gdc.aerodev.dao.UserDao;
import com.gdc.aerodev.dao.exception.DaoException;
import com.gdc.aerodev.dao.postgres.PostgresUserDao;
import com.gdc.aerodev.model.User;
import com.gdc.aerodev.service.GenericUserService;
import com.gdc.aerodev.service.exception.ServiceException;
import com.gdc.aerodev.service.logging.LoggingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements GenericUserService, LoggingService {

    private final UserDao dao;

    public UserService(PostgresUserDao dao) {
        this.dao = dao;
    }

    public List<User> getTopThree() {
        return dao.getTopThree();
    }

    public void updateInfo(Long id, String firstName, String lastName, String biography, String userCountry, String userCity) {
        User user = dao.getById(id);
        user.setUserFirstName(firstName);
        user.setUserLastName(lastName);
        user.setUserBiography(biography);
        user.setUserCountry(userCountry);
        user.setUserCity(userCity);
        if (dao.save(user) == null){
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

    public int countUsers() {
        return dao.count();
    }

    private boolean isExistentName(String userName) {
        return dao.getByName(userName) != null;
    }

}
