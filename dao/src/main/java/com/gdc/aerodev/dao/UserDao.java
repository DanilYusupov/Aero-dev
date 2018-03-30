package com.gdc.aerodev.dao;

import com.gdc.aerodev.dao.exception.DaoException;
import com.gdc.aerodev.model.User;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Realization of data access object for working with {@code User} instance
 *
 * @author Yusupov Danil
 */
@Repository
public class UserDao implements GenericDao<User, Long> {

    private final JdbcTemplate jdbcTemplate;
    private String tableName;

    public UserDao(JdbcTemplate jdbcTemplate, String tableName) {
        this.jdbcTemplate = jdbcTemplate;
        this.tableName = tableName;
    }

    /**
     * Gets {@code User} entity from connected database with inserted {@param userId}
     * @param id identifier of target {@code User}
     * @return (0) found {@code User} with matched {@param id}
     *         (1) null if there is no such {@code User}
     */
    @Override
    public User getById(Long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM " + tableName + " WHERE userId = ?;",
                    (rs, rowNum) -> {
                        return buildUser(rs);
                    }, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * Gets {@code User} entity from connected database with inserted {@param userName}
     * @param name name of target {@code User}
     * @return (0) found {@code User} with matched {@param userName}
     *         (1) null if there is no such {@code User}
     */
    @Override
    public User getByName(String name) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM " + tableName + " WHERE userName = ?;",
                    (rs, rowNum) -> {
                        return buildUser(rs);
                    }, name);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM " + tableName + " ;",
                (rs, rowNum) -> {
                    return buildUser(rs);
                }
        );
    }

    /**
     * Inserts new {@code User} if {@param userId} is {@code null} or updates it if {@param userId} is {@code !null}
     * @param entity target {@code User} to insert or update in database
     * @return {@param userId} of inserted or updated {@code User}
     * @throws DaoException if {@param userName} or {@param userEmail} is already registered in database
     */
    @Override
    public Long save(User entity) {
        if (entity.getUserId() == null) {
            try {
                return insert(entity);
            } catch (DuplicateKeyException e){
                throw new DaoException("User '" + entity.getUserName() + "' is already registered with email: '"
                        + entity.getUserEmail() + "'.", e);
            }
        } else {
            return update(entity);
        }
    }

    private Long insert(User entity) {
        final String INSERT_SQL = "INSERT INTO " + tableName + " (userName, userPassword, userEmail) VALUES (?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                con -> {
                    PreparedStatement ps = con.prepareStatement(INSERT_SQL, new String[]{"userid"});
                    ps.setString(1, entity.getUserName());
                    ps.setString(2, entity.getUserPassword());
                    ps.setString(3, entity.getUserEmail());
                    return ps;
                },
                keyHolder
        );
        return keyHolder.getKey().longValue();
    }

    private Long update(User entity) {
        int rows = jdbcTemplate.update("UPDATE " + tableName +
                        " SET userName=?, userPassword=?, userEmail=?, userLevel=? WHERE userId = "
                        + entity.getUserId() + ";",
                entity.getUserName(),
                entity.getUserPassword(),
                entity.getUserEmail(),
                entity.getUserLevel());
        return (rows > 0) ? entity.getUserId() : null;
    }

    /**
     * Deletes {@code User} entity from connected database by inserted {@param userId}
     * @param id identifier of target {@code User}
     * @return (0) {@code true} if deleting was performed or
     *         (1) {@code false} if nothing was deleted
     */
    @Override
    public boolean delete(Long id) {
        int rows = jdbcTemplate.update("DELETE FROM " + tableName + " WHERE userId = ?;", id);
        return rows > 0;
    }

    /**
     * Utility method, which builds {@code User} entity from inserted {@code ResultSet}
     * @param rs incoming {@code ResultSet}
     * @return built {@code User} entity
     * @throws SQLException if build was performed incorrectly (see stacktrace)
     */
    private User buildUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getLong("userId"));
        user.setUserName(rs.getString("userName"));
        user.setUserPassword(rs.getString("userPassword"));
        user.setUserEmail(rs.getString("userEmail"));
        user.setUserLevel(rs.getShort("userLevel"));
        return user;
    }
}
