package com.gdc.aerodev.service;

import com.gdc.aerodev.model.ProjectContent;

import java.util.Date;

/**
 * This service works with {@code ProjectContent} entities, which contain detailed information about {@code Project}
 *
 * @see com.gdc.aerodev.model.Project
 * @see com.gdc.aerodev.model.ProjectContent
 * @see com.gdc.aerodev.dao.ProjectContentDao
 * @author Yusupov Danil
 */
public interface ProjectContentService {

    boolean createProjectContent(Long projectId, byte[] projectLogo, String projectDescription, Date projectBirth);

    boolean updateProjectContent(Long projectId, byte[] projectLogo, String projectDescription, Date projectBirth);

    ProjectContent get(Long projectId);

}
