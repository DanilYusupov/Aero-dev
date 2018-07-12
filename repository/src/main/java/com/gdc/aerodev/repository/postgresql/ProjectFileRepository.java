package com.gdc.aerodev.repository.postgresql;

import com.gdc.aerodev.model.Project;
import com.gdc.aerodev.model.ProjectFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectFileRepository extends JpaRepository<ProjectFile, Long> {
    List<ProjectFile> findAllByProject(Project project);
}
