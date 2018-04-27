package com.gdc.aerodev.dao.postgres;

import com.gdc.aerodev.dao.AvatarDao;
import com.gdc.aerodev.dao.exception.DaoException;
import com.gdc.aerodev.model.Avatar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayInputStream;
import java.sql.*;

import static com.gdc.aerodev.dao.postgres.DaoMaintenance.getTableName;
import static com.gdc.aerodev.dao.postgres.DaoMaintenance.toByteArray;

@Repository
public class PostgresAvatarDao implements AvatarDao, Postgresqlable<Avatar, Long> {
    /**
     * Autowired on application run, but initialized evidently in test cases
     */
    private JdbcTemplate jdbcTemplate;

    /**
     * Gets from classpath:/db.properties as 'avatar.table' property. <br>
     * For test cases initializes evidently according to migration files
     */
    private String tableName;

    private final String SELECT_QUERY = "SELECT av_id, av_owner, av_data, av_type FROM ";

    @Autowired
    public PostgresAvatarDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.tableName = getTableName("avatar.table");
    }

    public PostgresAvatarDao(JdbcTemplate jdbcTemplate, String tableName) {
        this.jdbcTemplate = jdbcTemplate;
        this.tableName = tableName;
    }

    /**
     * Gives user's avatar by his id
     *
     * @param id identifier of avatar owner {@code User}
     * @return (0) {@code Avatar} or
     * (1) {@code null}
     */
    @Override
    public Avatar getById(Long id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_QUERY + tableName + " WHERE av_owner = ?;",
                    new AvatarRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * Deletes user's avatar by his id
     *
     * @param id identifier of user which avatar must be deleted
     * @return (0) {@code true} if avatar was deleted or
     * (1) {@code false} if avatar wasn't deleted
     */
    @Override
    public boolean delete(Long id) {
        int rows = jdbcTemplate.update("DELETE FROM " + tableName + " WHERE av_owner = ?;", id);
        return rows > 0;
    }

    @Override
    public Long insert(Avatar entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                con -> {
                    PreparedStatement ps = con.prepareStatement("INSERT INTO " + tableName +
                            " (av_owner, av_data, av_type) VALUES (?, ?, ?);", new String[]{"av_id"});
                    ps.setLong(1, entity.getAvatarOwner());
                    log.info("Received new avatar with size: " + entity.getAvatarData().length + " bytes.");
                    ps.setBinaryStream(2, new ByteArrayInputStream(entity.getAvatarData()));
                    ps.setString(3, entity.getContentType());
                    return ps;
                },
                keyHolder
        );
        long id = keyHolder.getKey().longValue();
        log.info("Inserted avatar with id " + id);
        return id;
    }

    @Override
    public Long update(Avatar entity) {
        Long id = entity.getAvatarId();
        int rows = jdbcTemplate.update(
                con -> {
                    PreparedStatement ps = con.prepareStatement("UPDATE " + tableName +
                            " SET av_owner=?, av_data=?, av_type=? WHERE av_id=?;");
                    ps.setLong(1, entity.getAvatarOwner());
                    if (entity.getAvatarData() == null) {
                        throw new DaoException("Empty byte array!");
                    } else {
                        ps.setBinaryStream(2,
                                new ByteArrayInputStream(entity.getAvatarData()), entity.getAvatarData().length);
                    }
                    ps.setString(3, entity.getContentType());
                    ps.setLong(4, id);
                    return ps;
                }
        );
        if (rows > 0) {
            log.info("Updated avatar with id " + id);
            return id;
        } else {
            log.error("Nothing to update. Avatar id " + id);
            throw new DaoException("Nothing to update. Avatar id" + id);
        }
    }

    @Override
    public boolean isNew(Avatar entity) {
        return entity.getAvatarId() == null;
    }

    private static class AvatarRowMapper implements RowMapper<Avatar> {
        /**
         * Utility method, which builds {@code Avatar} entity from inserted {@code ResultSet}
         *
         * @param resultSet incoming {@code ResultSet}
         * @return built {@code Avatar} entity
         * @throws SQLException if build was performed incorrectly (see stacktrace)
         */
        @Override
        public Avatar mapRow(ResultSet resultSet, int i) throws SQLException {
            Avatar avatar = new Avatar();
            avatar.setAvatarId(resultSet.getLong("av_id"));
            avatar.setAvatarOwner(resultSet.getLong("av_owner"));
            avatar.setAvatarData(toByteArray(resultSet.getBinaryStream("av_data")));
            avatar.setContentType(resultSet.getString("av_type"));
            return avatar;
        }
    }
}
