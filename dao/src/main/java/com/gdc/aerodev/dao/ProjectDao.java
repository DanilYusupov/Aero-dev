package com.gdc.aerodev.dao;

import com.gdc.aerodev.model.Project;

import java.util.List;

/**
 * DAO interface for working with {@code Project} entity
 *
 * @see com.gdc.aerodev.model.Project
 * @author Yusupov Danil
 */
public interface ProjectDao extends GenericDao<Project, Long>{

    /**
     * Finds all projects created by {@code User}.
     * @param userId {@code User}'s id mapped as projectOwner parameter of {@code Project}
     * @return list of user's projects
     */
    List<Project> getByUserId(Long userId);

    /**
     * Counts all number of entities in table with simple query which returns {@code int}
     * @return number of entities in table
     */
    int count();

    /**
     * @return three high rated {@code Project}'s based on their rating.
     */
    List<Project> getTopThree();
}
