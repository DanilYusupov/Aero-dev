package com.gdc.aerodev.repository.postgresql;

import com.gdc.aerodev.model.Project;
import com.gdc.aerodev.model.ProjectImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectImageRepository extends JpaRepository<ProjectImage, Long> {

    List<ProjectImage> findAllByProject(Project project);

}
