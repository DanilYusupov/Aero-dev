package com.gdc.aerodev.dao;

import com.gdc.aerodev.model.ProjectFiles;

import java.util.List;

/**
 * Describes data access object to work with {@code ProjectFiles}
 *
 * @see ProjectFiles
 * @author Yusupov Danil
 */
public interface ProjectFilesDao extends GenericDao<ProjectFiles, Long> {

    /**
     * No reason to get this sub entity by name
     * @param name name of target {@code ProjectFiles}
     * @return {@code null}
     */
    @Deprecated
    @Override
    default ProjectFiles getByName(String name){
        return null;
    }

    /**
     * There is no reason to get an array of such entities yet.
     * @return {@code null}
     */
    @Deprecated
    @Override
    default List<ProjectFiles> getAll(){
        return null;
    }
}
