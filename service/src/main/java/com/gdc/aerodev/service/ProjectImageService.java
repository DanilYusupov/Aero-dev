package com.gdc.aerodev.service;

import com.gdc.aerodev.model.ProjectImage;
import com.gdc.aerodev.service.logging.LoggingService;

import java.util.List;

/**
 * This service works with project's images, which can be shown in project page
 *
 * @see com.gdc.aerodev.model.Project
 * @see com.gdc.aerodev.model.ProjectImage
 * @see com.gdc.aerodev.dao.ProjectImageDao
 * @author Yusupov Danil
 */
public interface ProjectImageService extends LoggingService{

    /**
     * Saves {@code ProjectImage} entity to connected DB
     * @param projectId id of target project
     * @param image image data
     * @param contentType type of image
     * @return {@code img_id} of saved image
     * @throws NullPointerException if save wasn't performed
     */
    Long createImage(Long projectId, byte[] image, String contentType);

    /**
     * Deletes image by it's id
     * @param imageId id of image to be delete
     * @return (0) {@code true} if delete complete or
     *         (1) {@code false} if image isn't deleted
     */
    boolean deleteImage(Long imageId);

    /**
     * Takes all images bind with one project
     * @param projectId id of target project
     * @return list of existent images
     */
    List<ProjectImage> getAll(Long projectId);

    /**
     * Gives image by it's id
     * @param imageId id of target image
     * @return image
     */
    ProjectImage get(Long imageId);

}
