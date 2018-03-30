package com.gdc.aerodev.service.specific;

import com.gdc.aerodev.dao.specific.UserDao;
import com.gdc.aerodev.service.GenericService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserService extends GenericService{

    private final UserDao dao;

    public UserService() {
        this.dao = new UserDao(new JdbcTemplate(), getTableName("user.table"));
    }

    public String createUser(String userName, String userPassword, String userEmail){
        if (dao.getByName(userName) != null){
            return "User with name '" + userName + "' is already exists.";
        }
        return null;
    }

}
