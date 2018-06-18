package com.gdc.aerodev.service.impl;

import com.gdc.aerodev.dao.ProjectDao;
import com.gdc.aerodev.dao.exception.DaoException;
import com.gdc.aerodev.model.Project;
import com.gdc.aerodev.model.ProjectType;
import com.gdc.aerodev.service.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of service for managing projects in database
 *
 * @author Yusupov Danil
 * @see ProjectService
 * @see ProjectDao
 * @see Project
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectDao dao;

    public ProjectServiceImpl(ProjectDao dao) {
        this.dao = dao;
    }

    @Override
    public Long createProject(String projectName, Long projectOwner, ProjectType projectType) {
        if (projectName.equals("") || projectOwner == null) {
            return null;
        }
        if (isExistentName(projectName)) {
            log.error("Project with name '" + projectName + "' is already exists.");
            return null;
        }
        try {
            Long id = dao.save(new Project(projectName, projectOwner, projectType));
            log.info("Project '" + projectName + "' created with id " + id + ".");
            return id;
        } catch (DaoException e) {
            return null;
            //FIXME: fix exception handling
        }
    }

    @Override
    public Long updateProject(Long projectId, String projectName, ProjectType projectType) {
        Project project = dao.getById(projectId);
        if (!projectName.equals("")) {
            if (isExistentName(projectName)) {
                log.error("Project with name '" + projectName + "' is already exists.");
                return null;
            }
            project.setProjectName(projectName);
        }
        project.setProjectType(projectType);
        try {
            log.info("Project '" + projectName + "' successfully updated.");
            return dao.save(project);
        } catch (DaoException e) {
            return null;
        }
    }

    @Override
    public Project getProject(String name) {
        return dao.getByName(name);
    }

    @Override
    public Project getProject(Long id) {
        return dao.getById(id);
    }

    @Override
    public boolean isOwner(Project project, Long userId) {
        return project.getProjectOwner().equals(userId);
    }

    @Override
    public List<Project> getByUserId(Long id) {
        return dao.getByUserId(id);
    }

    @Override
    public int countProjects() {
        return dao.count();
    }

    @Override
    public List<Project> getTopThree() {
        return dao.getTopThree();
    }

    /**
     * Check name existence for avoid name duplicating
     *
     * @param projectName name for search
     * @return (0) {@code true} if there is already got project with this name <br>
     * (1) {@code false} if there is no project with matching name
     */
    private boolean isExistentName(String projectName) {
        return dao.getByName(projectName) != null;
    }
}
