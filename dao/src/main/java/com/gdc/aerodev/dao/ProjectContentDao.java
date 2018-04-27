package com.gdc.aerodev.dao;

import com.gdc.aerodev.dao.exception.DaoException;
import com.gdc.aerodev.dao.logging.LoggingDao;
import com.gdc.aerodev.model.ProjectContent;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;

/**
 * This is data access object for {@code ProjectContent} entity
 *
 * @author Yusupov Danil
 * @see ProjectContent
 */
public interface ProjectContentDao extends GenericDao<ProjectContent, Long>, LoggingDao {
    /**
     * There is no reason to get this entity by name.
     *
     * @return {@code null}
     */
    @Deprecated
    @Override
    default ProjectContent getByName(String name) {
        return null;
    }

    /**
     * No reason to get a list of sub entities of {@code Project entity}
     *
     * @return {@code null}
     */
    @Deprecated
    @Override
    default List<ProjectContent> getAll() {
        return null;
    }

    /**
     * Saves or updates {@code ProjectContent} entity. Override, because there is necessary isNew() check.
     * {@code ProjectContent} has projectId parameter as {@code FOREIGN KEY} to {@code Project} entity and also
     * {@code PRIMARY KEY}, because one project can have only one {@code ProjectContent}
     *
     * @param entity target {@code ProjectContent} to insert or update in database
     * @return (0) id of saved or updated {@code ProjectContent} or <br>
     * (1) {@code null} if entity isn't saved or updated
     */
    @Override
    default Long save(ProjectContent entity) {
        if (isNew(entity.getProjectId())) {
            try {
                return insert(entity);
            } catch (DuplicateKeyException e) {
                throw new DaoException("Error inserting ProjectContent: ", e);
            }
        } else {
            return update(entity);
        }
    }

    /**
     * Performs {@code UPDATE} method in database to update entity
     *
     * @param entity existent in database {@code ProjectContent} for update
     * @return id of updated {@code ProjectContent}
     */
    Long update(ProjectContent entity);

    /**
     * Performs {@code INSERT} method im database to save new entity.<br>
     * <b>Important:</b> there is already {@code Project} entity with respective id must be existent in database
     *
     * @param entity not existent in database {@code ProjectContent}
     * @return (0) id of saved {@code ProjectContent} or <br>
     * (1) {@code null} if entity isn't saved
     */
    Long insert(ProjectContent entity);

    /**
     * Checks existence of {@code ProjectContent} with provided id in database
     *
     * @param projectId id of target {@code ProjectContent}
     * @return (0) {@code true} if {@code ProjectContent} is existent in database or <br>
     * (1) {@code false} if there is no {@code ProjectContent} in database with this id
     */
    boolean isNew(Long projectId);
}
