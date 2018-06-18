package com.gdc.aerodev.service;

import com.gdc.aerodev.model.Project;
import com.gdc.aerodev.model.ProjectType;
import com.gdc.aerodev.service.logging.LoggingService;

import java.util.List;

/**
 * Generic interface of service, which makes manipulations with {@code Project} entity.
 *
 * @author Yusupov Danil
 * @see com.gdc.aerodev.model.Project
 * @see com.gdc.aerodev.dao.ProjectDao
 */
public interface ProjectService extends LoggingService {
    /**
     * Inserts {@code Project} into database configured by input parameters.
     *
     * @param projectName  name of new {@code Project}
     * @param projectOwner ID of {@code User}, who created this {@code Project}
     * @param projectType  {@code ProjectType} of current {@code Project}
     * @return (0) {@param projectId} of inserted {@code Project} <br>
     * (1) or {@code null} in cause of problems
     */
    Long createProject(String projectName, Long projectOwner, ProjectType projectType);

    /**
     * Updates existent {@code Project} chosen by {@param projectId} with input parameters. If there is no need to
     * change some parameter, it should be left as empty ones.
     *
     * @param projectId   ID of updating {@code Project}
     * @param projectName new name of updating {@code Project}
     * @param projectType new {@code ProjectType} of updating {@code Project}
     * @return (0) {@param projectId} of updated {@code Project} <br>
     * (1) or {@code null} in cause of problems
     */
    Long updateProject(Long projectId, String projectName, ProjectType projectType);

    /**
     * Encapsulates same method in {@code ProjectDao}
     *
     * @param name of target project
     * @return (0) {@code Project} or <br>
     * (1) {@code null} if there is no such entity
     * @see com.gdc.aerodev.dao.ProjectDao
     */
    Project getProject(String name);

    /**
     * Encapsulates same method in {@code ProjectDao}
     *
     * @param id of target project
     * @return (0) {@code Project} or <br>
     * (1) {@code null} if there is no such entity
     * @see com.gdc.aerodev.dao.ProjectDao
     */
    Project getProject(Long id);

    /**
     * Encapsulates same method in {@code ProjectDao}
     *
     * @return list top three projects with biggest rating
     * @see com.gdc.aerodev.dao.ProjectDao
     */
    List<Project> getTopThree();

    /**
     * Encapsulates same method in {@code ProjectDao}
     *
     * @param userId owner of searching projects
     * @return list of projects created by {@code User} with {@code userId}
     * @see com.gdc.aerodev.dao.ProjectDao
     */
    List<Project> getByUserId(Long userId);

    /**
     * I don't know why I did this two weeks ago...
     *
     * @return number of all projects
     */
    int countProjects();

    /**
     * Checks project owner matching with client
     *
     * @param project target project to check
     * @param userId  id of current session client
     * @return (0) {@code true} if current client is owner of this project or <br>
     * (1) {@code false} if not...
     */
    boolean isOwner(Project project, Long userId);
}
