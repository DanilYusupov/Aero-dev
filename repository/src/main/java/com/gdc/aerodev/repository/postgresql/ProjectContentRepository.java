package com.gdc.aerodev.repository.postgresql;

import com.gdc.aerodev.model.ProjectContent;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ProjectContentRepository extends Repository<ProjectContent, Long> {
    List<ProjectContent> findAllByProjectId(Long projectId);
    ProjectContent save(ProjectContent projectContent);
    void deleteByProjectId(Long projectId);
}
