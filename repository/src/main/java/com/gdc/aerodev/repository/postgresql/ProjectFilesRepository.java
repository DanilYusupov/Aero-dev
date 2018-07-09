package com.gdc.aerodev.repository.postgresql;

import com.gdc.aerodev.model.ProjectFile;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ProjectFilesRepository extends Repository<ProjectFile, Long> {
    List<ProjectFile> findAllByProjectId(Long projectId);
    ProjectFile save(ProjectFile projectFile);
    void deleteByProjectId(Long projectId);
}
