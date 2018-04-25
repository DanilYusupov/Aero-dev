package com.gdc.aerodev.service.impl;

import com.gdc.aerodev.dao.ProjectContentDao;
import com.gdc.aerodev.dao.exception.DaoException;
import com.gdc.aerodev.dao.postgres.PostgresProjectContentDao;
import com.gdc.aerodev.model.Project;
import com.gdc.aerodev.model.ProjectContent;
import com.gdc.aerodev.service.ProjectContentService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProjectContentServiceImpl implements ProjectContentService {

    private ProjectContentDao contentDao;
    private final Long DEFAULT_PROJECT = 1L;

    public ProjectContentServiceImpl(PostgresProjectContentDao contentDao) {
        this.contentDao = contentDao;
    }

    @Override
    public boolean createProjectContent(Long projectId, byte[] projectLogo, String projectDescription, Date projectBirth) {
        try {
            if (projectDescription.equals("")){
                return false;
            }
        } catch (NullPointerException e){
            return false;
        }
        try {
            contentDao.save(new ProjectContent(projectId, projectLogo, projectDescription, new Date()));
            return true;
        } catch (DaoException e) {
            return false;
        }
    }

    @Override
    public boolean updateProjectContent(Long projectId, byte[] projectLogo, String projectDescription) {
        ProjectContent content = contentDao.getById(projectId);
        if (projectLogo.length != 0){
            content.setProjectLogo(projectLogo);
        }
        if (!projectDescription.equals("")){
            content.setProjectDescription(projectDescription);
        }
        try {
            contentDao.save(content);
            return true;
        } catch (DaoException e){
            return false;
        }
    }

    @Override
    public ProjectContent get(Long projectId) {
        ProjectContent content = contentDao.getById(projectId);
        if (content.getProjectLogo() == null || content.getProjectLogo().length == 0) {
            log.debug("No project logo for project with id: " + projectId);
            byte [] logo = contentDao.getById(DEFAULT_PROJECT).getProjectLogo();
            log.debug("Loaded default logo: " + logo.length + " bytes.");
            content.setProjectLogo(logo);
        }
        return content;
    }

    @Override
    public boolean isNew(Long projectId) {
        return contentDao.isNew(projectId);
    }

}
