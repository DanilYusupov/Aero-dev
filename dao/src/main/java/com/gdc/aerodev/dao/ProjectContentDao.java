package com.gdc.aerodev.dao;

import com.gdc.aerodev.model.ProjectContent;

import java.util.List;

/**
 * This is data access object for {@code ProjectContent} entity
 *
 * @see ProjectContent
 * @author Yusupov Danil
 */
public interface ProjectContentDao extends GenericDao<ProjectContent, Long>{
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
}
