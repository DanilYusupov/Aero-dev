package com.gdc.aerodev.dao;

import com.gdc.aerodev.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao implements GenericDao<User, Long> {

    private final JdbcTemplate jdbcTemplate;
    private String tableName;

    public UserDao(JdbcTemplate jdbcTemplate, String tableName) {
        this.jdbcTemplate = jdbcTemplate;
        this.tableName = tableName;
    }

    @Override
    public User getById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM " + tableName + " WHERE userId = ?;",
                (rs, rowNum) -> {
                    User user = new User();
                    user.setUserId(rs.getLong("userId"));
                    user.setUserName(rs.getString("userName"));
                    user.setUserPassword(rs.getString("userPassword"));
                    user.setUserEmail(rs.getString("userEmail"));
                    user.setUserLevel(rs.getShort("userLevel"));
                    return user;
                }, id);
    }

    @Override
    public User getByName(String name) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public Long save(User entity) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
