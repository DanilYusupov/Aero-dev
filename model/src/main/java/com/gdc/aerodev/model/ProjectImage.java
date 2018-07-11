package com.gdc.aerodev.model;

import javax.persistence.*;

/**
 * Entity contains image of {@code Project} entity
 *
 * @author Yusupov Danil
 * @see Project
 */
//@Entity
//@Table(name = "project_images")
public class ProjectImage {
//    @Id
//    @GeneratedValue (strategy = GenerationType.IDENTITY)
//    @Column(name = "img_id")
    private Long imageId;

    /**
     * This is {@code FOREIGN KEY} to {@code Project} entity.
     */
//    @Column(name = "prj_id")
    private Long projectId;

    /**
     * Image of {@code Project}
     */
//    @Column(name = "prj_image", nullable = false)
    private byte[] projectImage;

    /**
     * MIME type of image
     */
//    @Column(name = "img_type")
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

    public void setProjectImage(byte[] projectImage) {
        this.projectImage = projectImage;
    }
}
