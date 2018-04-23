package com.gdc.aerodev.dao.postgres;

import com.gdc.aerodev.dao.ProjectFilesDao;
import com.gdc.aerodev.model.ProjectFiles;

public class PostgresProjectFilesDao implements ProjectFilesDao, Daoable<ProjectFiles, Long> {

    //TODO:REALIZE CLASS!

    @Override
    public Long insert(ProjectFiles entity) {
        return null;
    }

    @Override
    public Long update(ProjectFiles entity) {
        return null;
    }

    @Override
    public boolean isNew(ProjectFiles entity) {
        return false;
    }

    @Override
    public ProjectFiles getById(Long id) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
