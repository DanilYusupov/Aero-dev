package com.gdc.aerodev.service.impl;

import com.gdc.aerodev.model.Project;
import com.gdc.aerodev.model.ProjectType;
import com.gdc.aerodev.model.User;
import com.gdc.aerodev.repository.postgresql.ProjectRepository;
import com.gdc.aerodev.repository.postgresql.UserRepository;
import com.gdc.aerodev.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of service for managing projects in database
 *
 * @author Yusupov Danil
 * @see ProjectService
 * @see Project
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository repository;
    private UserRepository userRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
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
            User user = userRepository.findByUserId(projectOwner);
            Long id = repository.save(new Project(projectName, projectType, user)).getProjectId();
            log.info("Project '" + projectName + "' created with id " + id + ".");
            return id;
        } catch (DataIntegrityViolationException e) {
            return null;
            //FIXME: No need to return long?!
        }
    }

    @Override
    public Long updateProject(Long projectId, String projectName, ProjectType projectType) {
        Project project = repository.findById(projectId).get();
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
            return repository.save(project).getProjectId();
        } catch (DataIntegrityViolationException e) {
            return null;
        }
    }

    @Override
    public Project getProject(String name) {
        return repository.findByProjectName(name);
    }

    @Override
    public Project getProject(Long id) {
        return repository.findByProjectId(id);
    }

    @Override
    public boolean isOwner(Project project, User user) {
        return project.getOwner().equals(user);
    }

    @Override
    public List<Project> getByUserId(User user) {
        return repository.findAllByOwner(user);
    }

    @Override
    public long countProjects() {
        return repository.count();
    }

    @Override
    public List<Project> getTopThree() {
        return repository.findAll(new PageRequest(0, 3)).getContent();
    }

    /**
     * Check name existence for avoid name duplicating
     *
     * @param projectName name for search
     * @return (0) {@code true} if there is already got project with this name <br>
     * (1) {@code false} if there is no project with matching name
     */
    private boolean isExistentName(String projectName) {
        return repository.findByProjectName(projectName) != null;
    }
}
