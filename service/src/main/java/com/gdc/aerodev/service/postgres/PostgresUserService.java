package com.gdc.aerodev.service.postgres;

import com.gdc.aerodev.dao.exception.DaoException;
import com.gdc.aerodev.dao.postgres.PostgresUserDao;
import com.gdc.aerodev.model.User;
import com.gdc.aerodev.service.util.TableManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class PostgresUserService implements com.gdc.aerodev.service.UserService{

    private final PostgresUserDao dao;

    @Autowired
    public PostgresUserService() {
        this.dao = new PostgresUserDao(new JdbcTemplate(), TableManager.getTableName("user.table"));
    }

    public PostgresUserService(DataSource dataSource){
        this.dao = new PostgresUserDao(new JdbcTemplate(dataSource));
    }

    @Override
    public Long createUser(String userName, String userPassword, String userEmail){
        if (userName.equals("") || userPassword.equals("") || userEmail.equals("")){
            return null;
        }
        if (isExistentName(userName)){
            return null;
            //TODO: plug in logger
//            return "User with name '" + userName + "' is already exists.";
        }
        String emailOwner = dao.existentEmail(userEmail);
        if (emailOwner != null){
            return null;
//            return "This email is already used by '" + emailOwner + "'.";
        }
        try {
            return dao.save(new User(userName, userPassword, userEmail));
//            return "Successful created user '" + userName + "' with id " + id + ".";
        } catch (DaoException e){
            return null;
        }
    }

    @Override
    public Long updateUser(Long userId, String userName, String userPassword, String userEmail, short userLevel){
        User user = dao.getById(userId);
        if (!userName.equals("")){
            if (isExistentName(userName)){
                return null;
//                return "User with name '" + userName + "' is already exists.";
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
                return null;
//                return "This email is already used by '" + emailOwner + "'.";
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

    public PostgresUserDao getDao(){
        return dao;
    }

    private boolean isExistentName(String userName){
        return dao.getByName(userName) != null;
    }

}
