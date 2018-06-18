package com.gdc.aerodev.service.impl;

import com.gdc.aerodev.dao.ProjectImageDao;
import com.gdc.aerodev.dao.postgres.PostgresProjectImageDao;
import com.gdc.aerodev.model.ProjectImage;
import com.gdc.aerodev.service.ProjectImageService;
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
    private ProjectImageDao imageDao;

    /**
     * Id of default image, which is already exists in database. <br>
     * This image will fill page of project without any images downloaded yet.
     */
    private final Long DEFAULT_IMAGE = 0L;

    public ProjectImageServiceImpl(PostgresProjectImageDao imageDao) {
        this.imageDao = imageDao;
    }

    @Override
    public Long createImage(Long projectId, byte[] image, String contentType) {
        if (image.length == 0 || contentType.equals("")) {
            return null;
        }
        return imageDao.save(new ProjectImage(projectId, image, contentType));
    }

    @Override
    public boolean deleteImage(Long imageId) {
        if (imageId == 0L) {
            return false;
        }
        return imageDao.delete(imageId);
    }

    @Override
    public List<Long> getAll(Long projectId) {
        List<Long> imagesId = imageDao.getAll(projectId);
        if (imagesId.isEmpty()) {
            log.debug("No images for project with id: " + projectId + ".");
            imagesId.add(DEFAULT_IMAGE);
            log.debug("Added default image's id to empty list.");
        }
        return imagesId;
    }

    @Override
    public ProjectImage get(Long imageId) {
        return imageDao.getById(imageId);
    }
}
