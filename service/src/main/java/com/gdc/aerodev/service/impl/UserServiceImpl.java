package com.gdc.aerodev.service.impl;

import com.gdc.aerodev.model.User;
import com.gdc.aerodev.repository.postgresql.UserRepository;
import com.gdc.aerodev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of service for managing users in database
 *
 * @author Yusupov Danil
 * @see UserService
 * @see User
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<User> getTopThree() {
        return repository.findAll(new PageRequest(0, 3)).getContent();
    }

    @Override
    public void updateInfo(Long id, String firstName, String lastName, String biography, String userCountry, String userCity) {
        User user = repository.findByUserId(id);
        user.setUserFirstName(firstName);
        user.setUserLastName(lastName);
        user.setUserBiography(biography);
        user.setUserCountry(userCountry);
        user.setUserCity(userCity);
        if (repository.save(user) == null) {
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
        User emailOwner = repository.findByUserEmail(userEmail);
        if (emailOwner != null) {
            log.error("This email is already used by '" + emailOwner.getUserName() + "' with id " + emailOwner.getUserId()+ ".");
            return null;
        }
        try {
            Long id = repository.save(new User(userName, userPassword, userEmail, isMale)).getUserId();
            log.info("Successful created user '" + userName + "' with id " + id + ".");
            return id;
        } catch (DataIntegrityViolationException e) {
            return null;
        }
    }

    @Override
    public Long updateUser(Long userId, String userName, String userPassword, String userEmail, short userLevel) {
        User user = repository.findByUserId(userId);
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
            User emailOwner = repository.findByUserEmail(userEmail);
            if (emailOwner != null) {
                log.error("This email is already used by '" + emailOwner.getUserName() + "' with id " + emailOwner.getUserId() + ".");
                return null;
            }
            user.setUserEmail(userEmail);
        }
        user.setUserLevel(userLevel);
        try {
            return repository.save(user).getUserId();
        } catch (DataIntegrityViolationException e) {
            return null;
        }
    }

    @Override
    public User getUser(String name) {
        return repository.findByUserName(name);
    }

    @Override
    public User getUser(Long id) {
        return repository.findByUserId(id);
    }

    @Override
    public long countUsers() {
        return repository.count();
    }

    /**
     * Check name existence for avoid name duplicating
     *
     * @param userName name for search
     * @return (0) {@code true} if there is already got user with this name <br>
     * (1) {@code false} if there is no user with matching name
     */
    private boolean isExistentName(String userName) {
        return repository.findByUserName(userName) != null;
    }

}
