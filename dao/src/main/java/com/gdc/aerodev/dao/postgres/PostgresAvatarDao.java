package com.gdc.aerodev.dao.postgres;

import com.gdc.aerodev.dao.exception.DaoException;
import com.gdc.aerodev.model.Avatar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;

public class PostgresAvatarDao extends AbstractDao<Avatar, Long> {

    private JdbcTemplate jdbcTemplate;
    private String tableName;
    private final String SELECT_QUERY = "SELECT av_id, av_owner, av_data, av_type FROM ";
    private final String INSERT_SQL = "INSERT INTO " + tableName + " (av_owner, av_data, av_type) VALUES (?, ?, ?);";

    @Autowired
    public PostgresAvatarDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.tableName = getTableName("avatar.table");
    }

    public PostgresAvatarDao(JdbcTemplate jdbcTemplate, String tableName) {
        this.jdbcTemplate = jdbcTemplate;
        this.tableName = tableName;
    }

    public Avatar getAvatar(Long id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_QUERY + tableName + " WHERE av_id = ?;", new AvatarRowMapper(), id);
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    protected Long insert(Avatar entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                con -> {
                    Blob blob = con.createBlob();
                    PreparedStatement ps = con.prepareStatement(INSERT_SQL, new String[]{"av_id"});
                    ps.setLong(1, entity.getAvatarOwner());
                    if (entity.getAvatarData() == null) {
                        ps.setNull(2, Types.BLOB);
                    } else {
                        blob.setBytes(1, entity.getAvatarData());
                        ps.setBlob(2, blob);
                    }
                    ps.setString(3, entity.getContentType());
                    return ps;
                },
                keyHolder
        );
        return keyHolder.getKey().longValue();
    }

    @Override
    protected Long update(Avatar entity) {
        int rows = jdbcTemplate.update(
                con -> {
                    Blob blob = con.createBlob();
                    PreparedStatement ps = con.prepareStatement("UPDATE " + tableName + " SET av_owner=?, av_data=?, av_type=? WHERE av_id = " + entity.getAvatarId() + ";");
                    ps.setLong(1, entity.getAvatarOwner());
                    if (entity.getAvatarData() == null) {
                        ps.setNull(2, Types.BLOB);
                    } else {
                        blob.setBytes(1, entity.getAvatarData());
                        ps.setBlob(2, blob);
                    }
                    ps.setString(3, entity.getContentType());
                    return ps;
                }
        );
        return (rows > 0) ? entity.getAvatarId() : null;
    }

    @Override
    protected boolean isNew(Avatar entity) {
        return entity.getAvatarId() == null;
    }

    private static class AvatarRowMapper implements RowMapper<Avatar> {

        @Override
        public Avatar mapRow(ResultSet resultSet, int i) throws SQLException {
            Avatar avatar = new Avatar();
            avatar.setAvatarId(resultSet.getLong("av_id"));
            avatar.setAvatarOwner(resultSet.getLong("av_owner"));
            Blob blob = resultSet.getBlob("av_data");
            if (blob == null) {
                throw new DaoException("No binary data in avatar: " + avatar.getAvatarId());
            }
            int length = (int) blob.length();
            byte[] avatarData = blob.getBytes(1, length);
            avatar.setAvatarData(avatarData);
            avatar.setContentType(resultSet.getString("av_type"));
            return avatar;
        }
    }

    @Override
    public int count() {
        return 0;
    }
}
