package com.gdc.aerodev.model;

import java.util.Date;

/**
 * This entity is strictly related to {@code Project} one and details it.
 *
 * @author Yusupov Danil
 * @see Project
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
     * Some information about project
     */
    private String projectDescription;

    /**
     * Date of project creation
     */
    private Date projectBirthDay;

    public ProjectContent(Long projectId, byte[] projectLogo, String projectDescription, Date projectBirthDay) {
        this.projectId = projectId;
        this.projectLogo = projectLogo;
        this.projectDescription = projectDescription;
        this.projectBirthDay = projectBirthDay;
    }

    public ProjectContent(Long projectId, byte[] projectLogo, String projectDescription) {
        this.projectId = projectId;
        this.projectLogo = projectLogo;
        this.projectDescription = projectDescription;
    }

    public Long getProjectId() {
        return projectId;
    }

    public byte[] getProjectLogo() {
        return projectLogo;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public Date getProjectBirthDay() {
        return projectBirthDay;
    }

    public void setProjectLogo(byte[] projectLogo) {
        this.projectLogo = projectLogo;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }
}
