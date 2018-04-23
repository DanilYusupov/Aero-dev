package com.gdc.aerodev.dao.postgres;

import com.gdc.aerodev.dao.UserDao;
import com.gdc.aerodev.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.gdc.aerodev.dao.postgres.DaoMaintenance.getTableName;

/**
 * Realization of data access object for working with {@code User} instance
 *
 * @author Yusupov Danil
 * @see User
 */
@Repository
public class PostgresUserDao implements UserDao, Daoable<User, Long> {

    private JdbcTemplate jdbcTemplate;
    private String tableName;
    private final String SELECT_QUERY = "SELECT usr_id, usr_name, usr_password, usr_email, usr_level," +
            " usr_first_name, usr_last_name, usr_biography, usr_rating, usr_country, usr_city, usr_is_male FROM ";

    private final String INSERT_PARAMS = " (usr_name, usr_password, usr_email," +
            " usr_first_name, usr_last_name, usr_biography, usr_country, usr_city, usr_is_male) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

    private final String UPDATE_PARAMS = " SET usr_name=?, usr_password=?, usr_email=?, usr_level=?," +
            " usr_first_name=?, usr_last_name=?, usr_biography=?, usr_rating=?, usr_country=?, usr_city=?, usr_is_male=?";

    @Autowired
    public PostgresUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.tableName = getTableName("user.table");
    }

    public PostgresUserDao(JdbcTemplate jdbcTemplate, String tableName) {
        this.jdbcTemplate = jdbcTemplate;
        this.tableName = tableName;
    }

    @Override
    public User getById(Long id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_QUERY + tableName + " WHERE usr_id = ?;",
                    new UserRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    @Override
    public User getByName(String name) {
        try {
            return jdbcTemplate.queryForObject(SELECT_QUERY + tableName + " WHERE usr_name = ?;",
                    new UserRowMapper(), name);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query(SELECT_QUERY + tableName + ";", new UserRowMapper());
    }

    public Long insert(User entity) {
        final String INSERT_SQL = "INSERT INTO " + tableName + INSERT_PARAMS;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                con -> {
                    PreparedStatement ps = con.prepareStatement(INSERT_SQL, new String[]{"usr_id"});
                    ps.setString(1, entity.getUserName());
                    ps.setString(2, entity.getUserPassword());
                    ps.setString(3, entity.getUserEmail());
                    ps.setString(4, entity.getUserFirstName());
                    ps.setString(5, entity.getUserLastName());
                    ps.setString(6, entity.getUserBiography());
                    ps.setString(7, entity.getUserCountry());
                    ps.setString(8, entity.getUserCity());
                    ps.setBoolean(9, entity.isMale());
                    return ps;
                },
                keyHolder
        );
        return keyHolder.getKey().longValue();
    }

    public Long update(User entity) {
        int rows = jdbcTemplate.update("UPDATE " + tableName + UPDATE_PARAMS + " WHERE usr_id = "
                        + entity.getUserId() + ";",
                entity.getUserName(),
                entity.getUserPassword(),
                entity.getUserEmail(),
                entity.getUserLevel(),
                entity.getUserFirstName(),
                entity.getUserLastName(),
                entity.getUserBiography(),
                entity.getUserRating(),
                entity.getUserCountry(),
                entity.getUserCity(),
                entity.isMale()
        );
        return (rows > 0) ? entity.getUserId() : null;
    }


    @Override
    public boolean delete(Long id) {
        int rows = jdbcTemplate.update("DELETE FROM " + tableName + " WHERE usr_id = ?;", id);
        return rows > 0;
    }

    @Override
    public boolean isNew(User entity) {
        return entity.getUserId() == null;
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM " + tableName + ";", Integer.class);
    }

    public String existentEmail(String userEmail) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT usr_name FROM " + tableName + " WHERE usr_email = ?;",
                    new Object[]{userEmail},
                    String.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<User> getTopThree() {
        return jdbcTemplate.query(SELECT_QUERY + tableName + " ORDER BY usr_rating DESC LIMIT 3;", new UserRowMapper());
        //TODO: add rating logic!
    }

    private static class UserRowMapper implements RowMapper<User> {
        /**
         * Utility method, which builds {@code User} entity from inserted {@code ResultSet}
         *
         * @param resultSet incoming {@code ResultSet}
         * @return built {@code User} entity
         * @throws SQLException if build was performed incorrectly (see stacktrace)
         */
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setUserId(resultSet.getLong("usr_id"));
            user.setUserName(resultSet.getString("usr_name"));
            user.setUserPassword(resultSet.getString("usr_password"));
            user.setUserEmail(resultSet.getString("usr_email"));
            user.setUserLevel(resultSet.getShort("usr_level"));
            user.setUserFirstName(resultSet.getString("usr_first_name"));
            user.setUserLastName(resultSet.getString("usr_last_name"));
            user.setUserBiography(resultSet.getString("usr_biography"));
            user.setUserRating(resultSet.getInt("usr_rating"));
            user.setUserCountry(resultSet.getString("usr_country"));
            user.setUserCity(resultSet.getString("usr_city"));
            user.setMale(resultSet.getBoolean("usr_is_male"));
            return user;
        }
    }
}
