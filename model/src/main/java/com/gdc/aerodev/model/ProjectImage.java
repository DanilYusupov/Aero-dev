package com.gdc.aerodev.model;

import javax.persistence.*;

/**
 * Entity contains image of {@code Project} entity
 *
 * @author Yusupov Danil
 * @see Project
 */
@Entity
@Table(schema = "aero", name = "project_images")
public class ProjectImage {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "img_id")
    private Long imageId;

    /**
     * This is {@code FOREIGN KEY} to {@code Project} entity.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prj_id", foreignKey = @ForeignKey(name = "img_fk"))
    private Project project;

    /**
     * Image of {@code Project}
     */
    @Column(name = "prj_image", nullable = false)
    private byte[] projectImage;

    /**
     * MIME type of image
     */
    @Column(name = "img_type")
    private String contentType;

    public ProjectImage(byte[] projectImage, String contentType) {
        this.projectImage = projectImage;
        this.contentType = contentType;
    }

    public ProjectImage(Long imageId, byte[] projectImage, String contentType) {
        this.imageId = imageId;
        this.projectImage = projectImage;
        this.contentType = contentType;
    }

    public Long getImageId() {
        return imageId;
    }

    public ProjectImage setImageId(Long imageId) {
        this.imageId = imageId;
        return this;
    }

    public Project getProject() {
        return project;
    }

    public ProjectImage setProject(Project project) {
        this.project = project;
        return this;
    }

    public byte[] getProjectImage() {
        return projectImage;
    }

    public ProjectImage setProjectImage(byte[] projectImage) {
        this.projectImage = projectImage;
        return this;
    }

    public String getContentType() {
        return contentType;
    }

    public ProjectImage setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }
}
