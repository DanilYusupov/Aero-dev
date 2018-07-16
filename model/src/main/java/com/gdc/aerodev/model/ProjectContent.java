package com.gdc.aerodev.model;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

/**
 * This entity is strictly related to {@code Project} one and details it.
 *
 * @author Yusupov Danil
 * @see Project
 */
@Entity
@Table(schema = "aero", name = "project_content")
public class ProjectContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long contentId;

    /**
     * This is {@code FOREIGN KEY} to {@code Project} id and also {@code PRIMARY KEY}, because one project can't have
     * two logos, descriptions and so on...
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prj_id")
    private Project project;

    /**
     * This param contains representative image of project. If it'll be {@code null}, then default logo will be shown.
     */
    @Column(name = "prj_logo")
    private byte[] projectLogo;

    /**
     * Some information about project
     */
    @Column(name = "prj_description")
    private String projectDescription;

    /**
     * Date of project creation
     */
    @Column(name = "prj_date")
    private Date projectBirthDay;

    public ProjectContent() {
    }

    public ProjectContent(byte[] projectLogo, String projectDescription, Date projectBirthDay) {
        this.projectLogo = projectLogo;
        this.projectDescription = projectDescription;
        this.projectBirthDay = projectBirthDay;
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

    public Project getProject() {
        return project;
    }

    public ProjectContent setProject(Project project) {
        this.project = project;
        return this;
    }

    public ProjectContent setProjectBirthDay(Date projectBirthDay) {
        this.projectBirthDay = projectBirthDay;
        return this;
    }

    public Long getContentId() {
        return contentId;
    }

    public ProjectContent setContentId(Long contentId) {
        this.contentId = contentId;
        return this;
    }
}
