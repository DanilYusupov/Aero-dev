package com.gdc.aerodev.service.impl;

import com.gdc.aerodev.model.Project;
import com.gdc.aerodev.model.ProjectImage;
import com.gdc.aerodev.repository.postgresql.ProjectImageRepository;
import com.gdc.aerodev.repository.postgresql.ProjectRepository;
import com.gdc.aerodev.service.ProjectImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of service for managing project's images in database
 *
 * @author Yusupov Danil
 * @see ProjectImageService
 * @see com.gdc.aerodev.model.Project
 * @see ProjectImage
 */
@Service
public class ProjectImageServiceImpl implements ProjectImageService {

    private ProjectImageRepository repository;
    private ProjectRepository projectRepository;

    /**
     * Id of default image, which is already exists in database. <br>
     * This image will fill page of project without any images downloaded yet.
     */
    private final Long DEFAULT_IMAGE = 0L;

    @Autowired
    public ProjectImageServiceImpl(ProjectImageRepository repository, ProjectRepository projectRepository) {
        this.repository = repository;
        this.projectRepository = projectRepository;
    }

    @Override
    public Long createImage(Long projectId, byte[] image, String contentType) {
        if (image.length == 0 || contentType.equals("")) {
            return null;
        }
        Project project = projectRepository.findByProjectId(projectId);
        return repository.save(new ProjectImage(project, image, contentType)).getImageId();
    }

    @Override
    public void deleteImage(Long imageId) {
        if (imageId == 0L) {
            return;
        }
        repository.deleteById(imageId);
    }

    @Override
    public List<ProjectImage> getAll(Long projectId) {
        Project project = projectRepository.findByProjectId(projectId);
        List<ProjectImage> imagesId = repository.findAllByProject(project);
        if (imagesId.isEmpty()) {
            log.debug("No images for project with id: " + projectId + ".");
            imagesId.add(repository.findById(DEFAULT_IMAGE).get());
            log.debug("Added default image's id to empty list.");
        }
        return imagesId;
    }

    @Override
    public ProjectImage get(Long imageId) {
        return repository.findById(imageId).get();
    }
}
