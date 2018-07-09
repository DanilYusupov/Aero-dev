package com.gdc.aerodev.repository.postgresql;

import com.gdc.aerodev.model.ProjectImage;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ProjectImagesRepository extends Repository<ProjectImage, Long> {
    List<ProjectImage> findAllByProjectId(Long projectId);
    ProjectImage findByImageId(Long imageId);
    ProjectImage save(ProjectImage projectImage);
    void deleteByImageId(Long imageId);
}
