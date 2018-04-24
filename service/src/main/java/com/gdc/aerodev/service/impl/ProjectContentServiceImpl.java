package com.gdc.aerodev.service.impl;

import com.gdc.aerodev.dao.ProjectContentDao;
import com.gdc.aerodev.dao.exception.DaoException;
import com.gdc.aerodev.dao.postgres.PostgresProjectContentDao;
import com.gdc.aerodev.model.ProjectContent;
import com.gdc.aerodev.service.ProjectContentService;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class ProjectContentServiceImpl implements ProjectContentService {

    private ProjectContentDao contentDao;

    public ProjectContentServiceImpl(PostgresProjectContentDao contentDao) {
        this.contentDao = contentDao;
    }

    @Override
    public boolean createProjectContent(Long projectId, byte[] projectLogo, String projectDescription, Date projectBirth) {
        if (projectDescription.equals("")){
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
        try {
            contentDao.save(new ProjectContent(projectId, projectLogo, projectDescription));
            return true;
        } catch (DaoException e){
            return false;
        }
    }

    @Override
    public ProjectContent get(Long projectId) {
        return contentDao.getById(projectId);
    }

    @Override
    public boolean isNew(Long projectId) {
        return contentDao.isNew(projectId);
    }

}
