package com.gdc.aerodev.model;

import java.util.Date;
import java.util.List;

/**
 * This entity is strictly related to {@code Project} one and details it.
 *
 * @see Project
 * @author Yusupov Danil
 */
public class ProjectContent {

    /**
     * This is {@code FOREIGN KEY} to {@code Project} id and also {@code PRIMARY KEY}, because one project can't have
     * two logos, descriptions and so on...
     */
    private Long projectId;

    /**
     * This param contains representative image of project. If it'll be {@code null}, then default logo will be shown.
     */
    private byte[] projectLogo;

    /**
     * Here is array of images, which will be shown in page's carousel
     */
    private List<byte[]> projectImages;

    /**
     * Some information about project
     */
    private String projectDescription;

    /**
     * Date of project creation
     */
    private Date projectBirthDay;

    public ProjectContent(byte[] projectLogo, List<byte[]> projectImages, String projectDescription, Date projectBirthDay) {
        this.projectLogo = projectLogo;
        this.projectImages = projectImages;
        this.projectDescription = projectDescription;
        this.projectBirthDay = projectBirthDay;
    }

    public ProjectContent(Long projectId, byte[] projectLogo, List<byte[]> projectImages, String projectDescription, Date projectBirthDay) {
        this.projectId = projectId;
        this.projectLogo = projectLogo;
        this.projectImages = projectImages;
        this.projectDescription = projectDescription;
        this.projectBirthDay = projectBirthDay;
    }

    public Long getProjectId() {
        return projectId;
    }

    public byte[] getProjectLogo() {
        return projectLogo;
    }

    public List<byte[]> getProjectImages() {
        return projectImages;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public Date getProjectBirthDay() {
        return projectBirthDay;
    }
}
