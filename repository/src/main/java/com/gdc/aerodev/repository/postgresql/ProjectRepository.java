package com.gdc.aerodev.repository.postgresql;

import com.gdc.aerodev.model.Project;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ProjectRepository extends Repository<Project, Long> {
    List<Project> findAll();
    List<Project> findAllByProjectOwner(Long userId);
    Project findByProjectId(Long projectId);
    Project findByProjectName(String projectName);
    Project save(Project project);
    void deleteByProjectId(Long projectId);
}
