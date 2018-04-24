package com.gdc.aerodev.model;

/**
 * Entity contains image of {@code Project} entity
 *
 * @see Project
 * @author Yusupov Danil
 */
public class ProjectImage {

    private Long imageId;
    /**
     * This is {@code FOREIGN KEY} to {@code Project} entity.
     */
    private Long projectId;

    /**
     * Image of {@code Project}
     */
    private byte[] projectImage;
    private String contentType;

    public ProjectImage(Long projectId, byte[] projectImage, String contentType) {
        this.projectId = projectId;
        this.projectImage = projectImage;
        this.contentType = contentType;
    }

    public ProjectImage(Long imageId, Long projectId, byte[] projectImage, String contentType) {
        this.imageId = imageId;
        this.projectId = projectId;
        this.projectImage = projectImage;
        this.contentType = contentType;
    }

    public Long getImageId() {
        return imageId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public byte[] getProjectImage() {
        return projectImage;
    }

    public String getContentType() {
        return contentType;
    }
}
