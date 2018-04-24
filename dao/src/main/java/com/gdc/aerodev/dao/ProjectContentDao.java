package com.gdc.aerodev.dao;

import com.gdc.aerodev.dao.exception.DaoException;
import com.gdc.aerodev.dao.logging.LoggingDao;
import com.gdc.aerodev.model.ProjectContent;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;

/**
 * This is data access object for {@code ProjectContent} entity
 *
 * @see ProjectContent
 * @author Yusupov Danil
 */
public interface ProjectContentDao extends GenericDao<ProjectContent, Long>, LoggingDao{
    /**
     * There is no reason to get this entity by name.
     * @return {@code null}
     */
    @Deprecated
    @Override
    default ProjectContent getByName(String name){
        return null;
    }

    /**
     * No reason to get a list of sub entities of {@code Project entity}
     * @return {@code null}
     */
    @Deprecated
    @Override
    default List<ProjectContent> getAll(){
        return null;
    }

    @Override
    default Long save(ProjectContent entity){
        if (isNew(entity.getProjectId())){
            try {
                return insert(entity);
            } catch (DuplicateKeyException e){
                throw new DaoException("Error inserting ProjectContent: ", e);
            }
        } else {
            return update(entity);
        }
    }

    Long update(ProjectContent entity);

    Long insert(ProjectContent entity);

    boolean isNew(Long projectId);
}
