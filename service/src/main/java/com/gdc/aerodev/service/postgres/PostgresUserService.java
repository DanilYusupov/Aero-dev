package com.gdc.aerodev.service.postgres;

import com.gdc.aerodev.dao.exception.DaoException;
import com.gdc.aerodev.dao.postgres.PostgresUserDao;
import com.gdc.aerodev.model.User;
import com.gdc.aerodev.service.UserService;
import com.gdc.aerodev.service.logging.LoggingService;
import org.springframework.stereotype.Service;

@Service
public class PostgresUserService implements UserService, LoggingService {

    private final PostgresUserDao dao;

    public PostgresUserService(PostgresUserDao dao) {
        this.dao = dao;
    }

    @Override
    public Long createUser(String userName, String userPassword, String userEmail){
        if (userName.equals("") || userPassword.equals("") || userEmail.equals("")){
            return null;
        }
        if (isExistentName(userName)){
            log.error("User with name '" + userName + "' is already exists.");
            return null;
        }
        String emailOwner = dao.existentEmail(userEmail);
        if (emailOwner != null){
            log.error("This email is already used by '" + emailOwner + "'.");
            return null;
        }
        try {
            Long id = dao.save(new User(userName, userPassword, userEmail));
            log.info("Successful created user '" + userName + "' with id " + id + ".");
            return id;
        } catch (DaoException e){
            return null;
        }
    }

    @Override
    public Long updateUser(Long userId, String userName, String userPassword, String userEmail, short userLevel){
        User user = dao.getById(userId);
        if (!userName.equals("")){
            if (isExistentName(userName)){
                log.error("User with name '" + userName + "' is already exists.");
                return null;
            }
            user.setUserName(userName);
        } else if (userPassword.equals("") && userEmail.equals("") && user.getUserLevel() == userLevel){
            return null;
        }
        if (!userPassword.equals("")){
            user.setUserPassword(userPassword);
        }
        if (!userEmail.equals("")){
            String emailOwner = dao.existentEmail(userEmail);
            if (emailOwner != null){
                log.error("This email is already used by '" + emailOwner + "'.");
                return null;
            }
            user.setUserEmail(userEmail);
        }
        user.setUserLevel(userLevel);
        try {
             return dao.save(user);
        } catch (DaoException e){
            return null;
        }
    }

    public boolean isExistent(String name){
        return dao.getByName(name) != null;
    }

    public PostgresUserDao getDao(){
        return dao;
    }

    private boolean isExistentName(String userName){
        return dao.getByName(userName) != null;
    }

}
