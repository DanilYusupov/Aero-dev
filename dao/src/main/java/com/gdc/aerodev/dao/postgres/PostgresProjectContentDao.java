package com.gdc.aerodev.dao.postgres;

import com.gdc.aerodev.dao.ProjectContentDao;
import com.gdc.aerodev.dao.exception.DaoException;
import com.gdc.aerodev.model.ProjectContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayInputStream;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.gdc.aerodev.dao.postgres.DaoMaintenance.getTableName;
import static com.gdc.aerodev.dao.postgres.DaoMaintenance.toByteArray;

/**
 * PostgreSQL realization of DAO for working with {@code ProjectContent} entity
 *
 * @author Yusupov Danil
 * @see ProjectContent
 */
@Repository
public class PostgresProjectContentDao implements ProjectContentDao, Postgresqlable<ProjectContent, Long> {

    private JdbcTemplate jdbcTemplate;
    private String tableName;
    private final String SELECT_QUERY = "SELECT prj_id, prj_logo, prj_description, prj_date FROM ";

    @Autowired
    public PostgresProjectContentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.tableName = getTableName("project.content.table");
    }

    public PostgresProjectContentDao(JdbcTemplate jdbcTemplate, String tableName) {
        this.jdbcTemplate = jdbcTemplate;
        this.tableName = tableName;
    }

    @Override
    public ProjectContent getById(Long id) {
        try {
            return jdbcTemplate.queryForObject(
                    SELECT_QUERY + tableName + " WHERE prj_id = ?;",
                    new ProjectContentRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Long insert(ProjectContent entity) {
        final String INSERT_SQL = "INSERT INTO " + tableName + " (prj_id, prj_logo, prj_description, prj_date) VALUES (?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                con -> {
                    PreparedStatement ps = con.prepareStatement(INSERT_SQL, new String[]{"prj_id"});
                    ps.setLong(1, entity.getProjectId());
                    log.info("Received new project logo for project with id: " + entity.getProjectId() + ". Size: " + entity.getProjectLogo().length + " bytes.");
                    ps.setBinaryStream(2, new ByteArrayInputStream(entity.getProjectLogo()));
                    ps.setString(3, entity.getProjectDescription());
                    ps.setDate(4, new Date(entity.getProjectBirthDay().getTime()));
                    return ps;
                },
                keyHolder
        );
        long id = keyHolder.getKey().longValue();
        log.info("Inserted project logo with project id: " + id);
         return id;
    }

    @Override
    public Long update(ProjectContent entity) {
        Long id = entity.getProjectId();
        int rows = jdbcTemplate.update(
                con -> {
                    PreparedStatement ps = con.prepareStatement("UPDATE " + tableName + " SET prj_logo=?, prj_description=? WHERE prj_id = ?;");
                    ps.setBinaryStream(1, new ByteArrayInputStream(entity.getProjectLogo()), entity.getProjectLogo().length);
                    ps.setString(2, entity.getProjectDescription());
                    ps.setLong(3, id);
                    return ps;
                }
        );
        if (rows > 0) {
            log.info("Updated project content with id " + id);
            return id;
        } else {
            log.error("Nothing to update. Project id " + id);
            throw new DaoException("Nothing to update. Project id" + id);
        }
    }

    @Override
    public boolean isNew(ProjectContent entity) {
        try {
            jdbcTemplate.queryForObject(SELECT_QUERY + tableName + " WHERE prj_id = ?;",
                    new ProjectContentRowMapper(),
                    entity.getProjectId());
            return false;
        } catch (EmptyResultDataAccessException e) {
            return true;
        }
    }

    @Override
    public boolean delete(Long id) {
        int rows = jdbcTemplate.update("DELETE FROM " + tableName + " WHERE prj_id = ?;", id);
        return rows > 0;
    }

    private static class ProjectContentRowMapper implements RowMapper<ProjectContent> {

        @Override
        public ProjectContent mapRow(ResultSet rs, int rowNum) throws SQLException {
            List<byte[]> imgs;
            return new ProjectContent(
                    rs.getLong("prj_id"),
                    toByteArray(rs.getBinaryStream("prj_logo")),
                    rs.getString("prj_description"),
                    new Date(rs.getDate("prj_date").getTime())
            );
        }
    }

}
