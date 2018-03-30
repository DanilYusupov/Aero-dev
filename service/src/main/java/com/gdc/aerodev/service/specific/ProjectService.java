package com.gdc.aerodev.service.specific;

import com.gdc.aerodev.dao.exception.DaoException;
import com.gdc.aerodev.dao.specific.ProjectDao;
import com.gdc.aerodev.model.Project;
import com.gdc.aerodev.model.ProjectType;
import com.gdc.aerodev.service.GenericService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProjectService extends GenericService {

    private final ProjectDao dao;

    public ProjectService() {
        this.dao = new ProjectDao(new JdbcTemplate(), getTableName("project.table"));
    }

    public String createProject(String projectName, Long projectOwner, ProjectType projectType, String projectDescription) {
        if (isExistentName(projectName)) {
            return "Project with name '" + projectName + "' is already exists.";
        }
        try {
            Long id = dao.save(new Project(projectName, projectOwner, projectType, projectDescription));
            return "Project '" + projectName + "' created with id " + id + ".";
        } catch (DaoException e) {
            return e.getMessage();
        }
    }

    public String updateProject(Long projectId, String projectName, ProjectType projectType, String projectDescription) {
        Project project = dao.getById(projectId);
        if (!projectName.equals("")) {
            if (isExistentName(projectName)) {
                return "Project with name '" + projectName + "' is already exists.";
            }
            project.setProjectName(projectName);
        }
        if (!projectDescription.equals("")){
            project.setProjectDescription(projectDescription);
        }
        project.setProjectType(projectType);
        try{
            dao.save(project);
            return "Project '" + projectName + "' successfully updated.";
        } catch (DaoException e){
            return e.getMessage();
        }
    }

    private boolean isExistentName(String projectName) {
        return dao.getByName(projectName) != null;
    }

}
