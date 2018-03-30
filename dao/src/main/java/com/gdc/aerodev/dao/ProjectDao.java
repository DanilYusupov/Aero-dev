package com.gdc.aerodev.dao;

import com.gdc.aerodev.dao.exception.DaoException;
import com.gdc.aerodev.model.Project;
import com.gdc.aerodev.model.ProjectType;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Realization of data access object for working with {@code Project} instance
 *
 * @author Yusupov Danil
 */
public class ProjectDao implements GenericDao<Project, Long> {

    private final JdbcTemplate jdbcTemplate;
    private String tableName;

    public ProjectDao(JdbcTemplate jdbcTemplate, String tableName) {
        this.jdbcTemplate = jdbcTemplate;
        this.tableName = tableName;
    }

    @Override
    public Project getById(Long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM " + tableName + " WHERE projectId = ?;",
                    (rs, rowNum) -> {
                        return buildProject(rs);
                    }, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Project getByName(String name) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM " + tableName + " WHERE projectName = ?;",
                    (rs, rowNum) -> {
                        return buildProject(rs);
                    }, name);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Project> getAll() {
        return jdbcTemplate.query("SELECT * FROM " + tableName + " ;",
                (rs, rowNum) -> {
                    return buildProject(rs);
                }
        );
    }

    @Override
    public Long save(Project entity) {
        if (entity.getProjectId() == null) {
            try {
                return insert(entity);
            } catch (DuplicateKeyException e){
                throw new DaoException("Project '" + entity.getProjectName() + "' is already registered.", e);
            }
        } else {
            return update(entity);
        }
    }

    private Long insert(Project entity) {
        final String INSERT_SQL = "INSERT INTO " + tableName + " (projectName, projectOwner, projectType, projectDescription) VALUES (?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                con -> {
                    PreparedStatement ps = con.prepareStatement(INSERT_SQL, new String[]{"projectid"});
                    ps.setString(1, entity.getProjectName());
                    ps.setLong(2, entity.getProjectOwner());
                    ps.setString(3, entity.getProjectType().toString());
                    ps.setString(4, entity.getProjectDescription());
                    return ps;
                },
                keyHolder
        );
        return keyHolder.getKey().longValue();
    }

    private Long update(Project entity) {
        int rows = jdbcTemplate.update("UPDATE " + tableName +
                        " SET projectName=?, projectOwner=?, projectType=?, projectDescription=? WHERE projectId = "
                        + entity.getProjectId() + ";",
                entity.getProjectName(),
                entity.getProjectOwner(),
                entity.getProjectType().toString(),
                entity.getProjectDescription());
        return (rows > 0) ? entity.getProjectId() : null;
    }

    @Override
    public boolean delete(Long id) {
        int rows = jdbcTemplate.update("DELETE FROM " + tableName + " WHERE projectId = ?;", id);
        return rows > 0;
    }

    private Project buildProject(ResultSet rs) throws SQLException {
        Project project = new Project();
        project.setProjectId(rs.getLong("projectId"));
        project.setProjectName(rs.getString("projectName"));
        project.setProjectOwner(rs.getLong("projectOwner"));
        project.setProjectType(ProjectType.valueOf(rs.getString("projectType")));
        project.setProjectDescription(rs.getString("projectDescription"));
        return project;
    }
}
