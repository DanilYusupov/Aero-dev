package com.gdc.aerodev.dao;

import com.gdc.aerodev.dao.logging.LoggingDao;
import com.gdc.aerodev.model.ProjectImage;

import java.util.List;

/**
 * Describes DAO for working with {@code ProjectImage} entity
 *
 * @see ProjectImage
 * @see com.gdc.aerodev.model.Project
 * @author Yusupov Danil
 */
public interface ProjectImageDao extends GenericDao<ProjectImage, Long>, LoggingDao {

    /**
     * There is no such param in entity
     * @return {@code null}
     */
    @Deprecated
    @Override
    default ProjectImage getByName(String name){
        return null;
    }

    /**
     * Method will be modified
     * @return {@code null}
     */
    @Deprecated
    @Override
    default List<ProjectImage> getAll(){
        return null;
    }

    /**
     * Gets all project's images
     * @param id of target project
     * @return list of {@code ProjectImage}s
     */
    List<ProjectImage> getAll(Long id);
}
