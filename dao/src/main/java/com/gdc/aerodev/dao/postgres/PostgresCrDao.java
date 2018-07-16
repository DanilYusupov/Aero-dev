package com.gdc.aerodev.dao.postgres;

import com.gdc.aerodev.dao.CrDao;
import com.gdc.aerodev.model.Cr;
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


@Repository
public class PostgresCrDao implements CrDao, Postgresqlable<Cr, Long> {

    private JdbcTemplate jdbcTemplate;
    /**
     * Gets from classpath:/db.properties as 'project.table' property. <br>
     * For test cases initializes evidently according to migration files
     */
    private String tableName;
    private final String SELECT_QUERY = "SELECT cr_id, cr_name, cr_pass, cr_email, cr_comp_id, cr_first_name, cr_last_name, cr_position FROM ";

    @Autowired
    public PostgresCrDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.tableName = getTableName("cr.table");
    }

    public PostgresCrDao(JdbcTemplate jdbcTemplate, String tableName) {
        this.jdbcTemplate = jdbcTemplate;
        this.tableName = tableName;
    }

    @Override
    public Long insert(Cr entity) {
        final String INSERT_SQL = "INSERT INTO " + tableName +
                " (cr_name, cr_pass, cr_email, cr_comp_id, cr_first_name, cr_last_name, cr_position) VALUES (?, ?, ?, ?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                con -> {
                    PreparedStatement ps = con.prepareStatement(INSERT_SQL, new String[]{"cr_id"});
                    ps.setString(1, entity.getCrName());
                    ps.setString(2, entity.getCrPassword());
                    ps.setString(3, entity.getCrEmail());
                    ps.setLong(4, entity.getCompanyId());
                    ps.setString(5, entity.getCrFirstName());
                    ps.setString(6, entity.getCrLastName());
                    ps.setString(7, entity.getCrPosition());
                    return ps;
                },
                keyHolder
        );
        return keyHolder.getKey().longValue();
    }

    @Override
    public Long update(Cr entity) {
        int rows = jdbcTemplate.update("UPDATE " + tableName +
                        " SET cr_name=?, cr_pass=?, cr_email=?, cr_comp_id=?, cr_first_name=?, cr_last_name=?, cr_position=? WHERE cr_id = "
                        + entity.getCrId() + ";",
                entity.getCrName(),
                entity.getCrPassword(),
                entity.getCrEmail(),
                entity.getCompanyId(),
                entity.getCrFirstName(),
                entity.getCrLastName(),
                entity.getCrPosition());
        return (rows > 0) ? entity.getCrId() : null;
    }

    @Override
    public boolean isNew(Cr entity) {
        return entity.getCrId() == null;
    }

    @Override
    public Cr getById(Long id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_QUERY + tableName + " WHERE cr_id = ?;",
                    new CrRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Cr getByName(String name) {
        try {
            return jdbcTemplate.queryForObject(SELECT_QUERY + tableName + " WHERE cr_name = ?;",
                    new CrRowMapper(), name);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Cr> getAll() {
        return jdbcTemplate.query(SELECT_QUERY + tableName + ";", new CrRowMapper());
    }

    @Override
    public boolean delete(Long id) {
        int rows = jdbcTemplate.update("DELETE FROM " + tableName + " WHERE cr_id = ?;", id);
        return rows > 0;
    }

    @Override
    public String existentEmail(String crEmail) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT cr_name FROM " + tableName + " WHERE cr_email = ?;",
                    new Object[]{crEmail},
                    String.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private static class CrRowMapper implements RowMapper<Cr>{
        @Override
        public Cr mapRow(ResultSet rs, int rowNum) throws SQLException {
            Cr cr = new Cr();
            cr.setCrId(rs.getLong("cr_id"));
            cr.setCrName(rs.getString("cr_name"));
            cr.setCrPassword(rs.getString("cr_pass"));
            cr.setCrEmail(rs.getString("cr_email"));
            cr.setCompanyId(rs.getLong("cr_comp_id"));
            cr.setCrFirstName(rs.getString("cr_first_name"));
            cr.setCrLastName(rs.getString("cr_last_name"));
            cr.setCrPosition(rs.getString("cr_position"));
            return cr;
        }
    }
}
