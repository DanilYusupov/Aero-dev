package com.gdc.aerodev.dao.postgres;

import com.gdc.aerodev.dao.OfferDao;
import com.gdc.aerodev.model.Offer;
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
import java.util.List;

import static com.gdc.aerodev.dao.postgres.DaoMaintenance.getTableName;

/**
 * Realization of data access object for working with {@code Project} instance
 *
 * @author Yusupov Danil
 * @see Offer
 */
@Repository
public class PostgresOfferDao implements OfferDao, Postgresqlable<Offer, Long> {
    /**
     * Autowired on application run, but initialized evidently in test cases
     */
    private JdbcTemplate jdbcTemplate;
    /**
     * Gets from classpath:/db.properties as 'project.table' property. <br>
     * For test cases initializes evidently according to migration files
     */
    private String tableName;
    private final String SELECT_QUERY = "SELECT off_id, off_usr_id, off_cr_id, off_description FROM ";

    @Autowired
    public PostgresOfferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.tableName = getTableName("offer.table");
    }

    public PostgresOfferDao(JdbcTemplate jdbcTemplate, String tableName) {
        this.jdbcTemplate = jdbcTemplate;
        this.tableName = tableName;
    }

    @Override
    public Long insert(Offer entity) {
        final String INSERT_SQL = "INSERT INTO " + tableName + " (off_usr_id, off_cr_id, off_description) VALUES (?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                con -> {
                    PreparedStatement ps = con.prepareStatement(INSERT_SQL, new String[]{"off_id"});
                    ps.setLong(1, entity.getOfferedUserId());
                    ps.setLong(2, entity.getOfferedCrId());
                    ps.setString(3, entity.getOfferDescription());
                    return ps;
                },
                keyHolder
        );
        return keyHolder.getKey().longValue();
    }

    @Override
    public Long update(Offer entity) {
        int rows = jdbcTemplate.update("UPDATE " + tableName +
                        " SET off_usr_id=?, off_cr_id=?, off_description=? WHERE off_id = "
                        + entity.getOfferId() + ";",
                entity.getOfferedUserId(),
                entity.getOfferedCrId(),
                entity.getOfferDescription());
        return (rows > 0) ? entity.getOfferId() : null;
    }

    @Override
    public boolean isNew(Offer entity) {
        return entity.getOfferId() == null;
    }

    @Override
    public Offer getById(Long id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_QUERY + tableName + " WHERE off_id = ?;",
                    new OfferRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Deprecated
    @Override
    public Offer getByName(String name) {
        return null;
    }

    @Override
    public List<Offer> getAll() {
        return jdbcTemplate.query(SELECT_QUERY + tableName + ";",
                new OfferRowMapper());
    }

    @Override
    public boolean delete(Long id) {
        int rows = jdbcTemplate.update("DELETE FROM " + tableName + " WHERE off_id = ?;", id);
        return rows > 0;
    }

    private static class OfferRowMapper implements RowMapper<Offer> {
        /**
         * Utility method, which builds {@code Offer} entity from inserted {@code ResultSet}
         *
         * @param resultSet incoming {@code ResultSet}
         * @return built {@code Offer} entity
         * @throws SQLException if build was performed incorrectly (see stacktrace)
         */
        @Override
        public Offer mapRow(ResultSet resultSet, int i) throws SQLException {
            Offer offer = new Offer();
            offer.setOfferId(resultSet.getLong("off_id"));
            offer.setOfferedUserId(resultSet.getLong("off_usr_id"));
            offer.setOfferedCrId(resultSet.getLong("off_cr_id"));
            offer.setOfferDescription(resultSet.getString("off_description"));
            return offer;
        }
    }
}
