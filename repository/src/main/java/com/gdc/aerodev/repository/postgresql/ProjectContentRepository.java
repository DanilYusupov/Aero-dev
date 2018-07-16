package com.gdc.aerodev.repository.postgresql;

import com.gdc.aerodev.model.Project;
import com.gdc.aerodev.model.ProjectContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectContentRepository extends JpaRepository<ProjectContent, Long> {
    ProjectContent findByProject(Project project);
}
