package com.gdc.aerodev.service.postgres;

import com.gdc.aerodev.dao.exception.DaoException;
import com.gdc.aerodev.dao.postgres.PostgresProjectDao;
import com.gdc.aerodev.model.Project;
import com.gdc.aerodev.model.ProjectType;
import com.gdc.aerodev.service.ProjectService;
import com.gdc.aerodev.service.util.TableManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class PostgresProjectService implements ProjectService {

    private final PostgresProjectDao dao;

    @Autowired
    public PostgresProjectService() {
        this.dao = new PostgresProjectDao(new JdbcTemplate(), TableManager.getTableName("project.table"));
    }

    public PostgresProjectService(DataSource dataSource){
        this.dao = new PostgresProjectDao(new JdbcTemplate(dataSource));
    }

    @Override
    public Long createProject(String projectName, Long projectOwner, ProjectType projectType, String projectDescription) {
        if (projectName.equals("") || projectOwner == null || projectDescription.equals("")) {
            return null;
        }
        if (isExistentName(projectName)) {
            return null;
            //TODO: plug in logger
//            return "Project with name '" + projectName + "' is already exists.";
        }
        try {
            return dao.save(new Project(projectName, projectOwner, projectType, projectDescription));
//            return "Project '" + projectName + "' created with id " + id + ".";
        } catch (DaoException e) {
            return null;
        }
    }

    @Override
    public Long updateProject(Long projectId, String projectName, ProjectType projectType, String projectDescription) {
        Project project = dao.getById(projectId);
        if (!projectName.equals("")) {
            if (isExistentName(projectName)) {
                return null;
//                return "Project with name '" + projectName + "' is already exists.";
            }
            project.setProjectName(projectName);
        } else if (projectDescription.equals("")){
            return null;
        }
        if (!projectDescription.equals("")){
            project.setProjectDescription(projectDescription);
        }
        project.setProjectType(projectType);
        try{
            return dao.save(project);
//            return "Project '" + projectName + "' successfully updated.";
        } catch (DaoException e){
            return null;
        }
    }

    public PostgresProjectDao getDao(){
        return dao;
    }

    private boolean isExistentName(String projectName) {
        return dao.getByName(projectName) != null;
    }

}
