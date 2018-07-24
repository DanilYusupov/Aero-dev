package com.gdc.aerodev.service.impl;

import com.gdc.aerodev.model.Project;
import com.gdc.aerodev.model.ProjectContent;
import com.gdc.aerodev.repository.postgresql.ProjectContentRepository;
import com.gdc.aerodev.repository.postgresql.ProjectRepository;
import com.gdc.aerodev.service.ProjectContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Implementation os service for managing project's info as referenced entity in database
 *
 * @author Yusupov Danil
 * @see ProjectContentService
 * @see com.gdc.aerodev.model.Project
 * @see ProjectContent
 */
@Service
public class ProjectContentServiceImpl implements ProjectContentService {

    private ProjectContentRepository contentRepository;
    private ProjectRepository projectRepository;

    /**
     * Id of default project, which id already exists in database for development purposes
     */
    private final Long DEFAULT_PROJECT = 1L;

    @Autowired
    public ProjectContentServiceImpl(ProjectContentRepository contentRepository, ProjectRepository projectRepository) {
        this.contentRepository = contentRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public boolean createProjectContent(Long projectId, byte[] projectLogo, String projectDescription, Date projectBirth) {
        try {
            if (projectDescription.equals("")) {
                return false;
            }
            // FIXME: 16.07.2018 need a lot of refactoring in all service method's logic
        } catch (NullPointerException e) {
            return false;
        }
        try {
            Project project = projectRepository.findByProjectId(projectId);
            contentRepository.save(new ProjectContent(project, projectLogo, projectDescription, new Date()));
            return true;
        } catch (DataIntegrityViolationException e) {
            return false;
        }
    }

    @Override
    public boolean updateProjectContent(Long projectId, byte[] projectLogo, String projectDescription) {
        ProjectContent content = contentRepository.findById(projectId).get();
        if (projectLogo.length != 0) {
            content.setProjectLogo(projectLogo);
        }
        if (!projectDescription.equals("")) {
            content.setProjectDescription(projectDescription);
        }
        try {
            contentRepository.save(content);
            return true;
        } catch (DataIntegrityViolationException e) {
            return false;
        }
    }

    @Override
    public ProjectContent get(Long projectId) {
        ProjectContent content = contentRepository.findById(projectId).get();
        if (content.getProjectLogo() == null || content.getProjectLogo().length == 0) {
            log.debug("No project logo for project with id: " + projectId);
            byte[] logo = contentRepository.findById(DEFAULT_PROJECT).get().getProjectLogo();
            log.debug("Loaded default logo: " + logo.length + " bytes.");
            content.setProjectLogo(logo);
        }
        return content;
    }

    @Override
    public boolean isNew(Long projectId) {
        return !contentRepository.existsById(projectId);
    }

}
