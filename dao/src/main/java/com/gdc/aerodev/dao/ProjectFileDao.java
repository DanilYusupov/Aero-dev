package com.gdc.aerodev.dao;

import com.gdc.aerodev.model.ProjectFile;
import java.util.List;

/**
 * Describes data access object to work with {@code ProjectFile}
 * @see ProjectFile
 * @author Yusupov Danil
 */
public interface ProjectFileDao extends GenericDao<ProjectFile, Long> {
    /**
     * No reason to get this sub entity by name
     * @param name name of target {@code ProjectFile}
     * @return {@code null}
     */
    @Deprecated
    @Override
    default ProjectFile getByName(String name){
        return null;
    }

    /**
     * There is no reason to get an array of such entities yet.
     * @return {@code null}
     */
    @Deprecated
    @Override
    default List<ProjectFile> getAll(){
        return null;
    }
}
