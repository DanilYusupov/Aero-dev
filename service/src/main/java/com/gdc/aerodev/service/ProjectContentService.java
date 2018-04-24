package com.gdc.aerodev.service;

import com.gdc.aerodev.model.ProjectContent;
import com.gdc.aerodev.service.logging.LoggingService;

import java.util.Date;

/**
 * This service works with {@code ProjectContent} entities, which contain detailed information about {@code Project}
 *
 * @see com.gdc.aerodev.model.Project
 * @see com.gdc.aerodev.model.ProjectContent
 * @see com.gdc.aerodev.dao.ProjectContentDao
 * @author Yusupov Danil
 */
public interface ProjectContentService extends LoggingService{

    /**
     * Saves {@code ProjectContent} entity into DB.
     * @param projectId id of target project
     * @param projectLogo image of project logo
     * @param projectDescription some text about project
     * @param projectBirth date of project creation
     * @return (0) {@code true} if entity saved or
     *         (1) {@code false} if not
     */
    boolean createProjectContent(Long projectId, byte[] projectLogo, String projectDescription, Date projectBirth);

    /**
     * Updates {@code ProjectContent} entity into DB.
     * @param projectId id of target project
     * @param projectLogo image of project logo
     * @param projectDescription some text about project
     * @return (0) {@code true} if entity updated or
     *         (1) {@code false} if not
     */
    boolean updateProjectContent(Long projectId, byte[] projectLogo, String projectDescription);

    /**
     * Takes {@code ProjectContent} from DB
     * @param projectId id of target project
     * @return {@code ProjectContent}
     */
    ProjectContent get(Long projectId);

    /**
     * Checks existence of this entity in DB
     * @param projectId id of target project
     * @return (0) {@code true} if entity isn't exists in DB or
     *         (1) {@code false} if entity already exists in DB
     */
    boolean isNew(Long projectId);
}
