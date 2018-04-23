package com.gdc.aerodev.dao.postgres;

import com.gdc.aerodev.dao.ProjectFileDao;
import com.gdc.aerodev.model.ProjectFile;

public class PostgresProjectFileDao implements ProjectFileDao, Postgresqlable<ProjectFile, Long> {

    //TODO:REALIZE CLASS!

    @Override
    public Long insert(ProjectFile entity) {
        return null;
    }

    @Override
    public Long update(ProjectFile entity) {
        return null;
    }

    @Override
    public boolean isNew(ProjectFile entity) {
        return false;
    }

    @Override
    public ProjectFile getById(Long id) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
