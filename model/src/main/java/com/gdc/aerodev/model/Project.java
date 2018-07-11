package com.gdc.aerodev.model;

import javax.persistence.*;

/**
 * This entity can be created only by {@code User}. But {@code Cr} can review it. Also any project can be rated by
 * {@code User} and {@code Cr} in future versions.
 *
 * @author Yusupov Danil
 * @see User
 * @see ProjectType
 */
//@Entity
//@Table(name = "projects")
public class Project {
    /**
     * {@code PRIMARY KEY} for this entity
     */
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "prj_id")
    private Long projectId;
//    @Column(name = "prj_name",  nullable = false)
    private String projectName;
    /**
     * Refers to {@code User} entity
     */
//    @Column(name = "prj_owner")
//    @ManyToOne
    private Long projectOwner;
    /**
     * {@code NOT NULL} from {@code ProjectType} enum
     */
//    @Column(name = "prj_type")
    private ProjectType projectType;

    public Project() {
    }

    public Project(Long projectId, String projectName, Long projectOwner, ProjectType projectType) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.projectOwner = projectOwner;
        this.projectType = projectType;
    }

    public Project(String projectName, Long projectOwner, ProjectType projectType) {
        this.projectName = projectName;
        this.projectOwner = projectOwner;
        this.projectType = projectType;
    }

    public Long getProjectId() {
        return projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public Long getProjectOwner() {
        return projectOwner;
    }

    public ProjectType getProjectType() {
        return projectType;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setProjectOwner(Long projectOwner) {
        this.projectOwner = projectOwner;
    }

    public void setProjectType(ProjectType projectType) {
        this.projectType = projectType;
    }
}
