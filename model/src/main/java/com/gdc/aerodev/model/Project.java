package com.gdc.aerodev.model;

import com.sun.istack.internal.NotNull;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This entity can be created only by {@code User}. But {@code Cr} can review it. Also any project can be rated by
 * {@code User} and {@code Cr} in future versions.
 *
 * @author Yusupov Danil
 * @see User
 * @see ProjectType
 */
@Entity
@Table(schema = "aero", name = "projects")
public class Project {
    /**
     * {@code PRIMARY KEY} for this entity
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prj_id")
    private Long projectId;
    @Column(name = "prj_name",  nullable = false)
    private String projectName;
    /**
     * {@code NOT NULL} from {@code ProjectType} enum
     */
    @Column(name = "prj_type")
    @Enumerated(EnumType.STRING)
    private ProjectType projectType;

    //JPA relations below
    @ManyToOne()
    @JoinColumn(name = "usr_id", foreignKey = @ForeignKey(name = "owner_id_fk"))
    @NotNull
    private User owner;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "project", fetch = FetchType.LAZY, orphanRemoval = true)
    private ProjectContent content;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ProjectImage> images;

    public Project() {
    }

    public Project(Long projectId, String projectName, ProjectType projectType) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.projectType = projectType;
    }

    public Project(String projectName, ProjectType projectType, User owner) {
        this.projectName = projectName;
        this.projectType = projectType;
        this.owner = owner;
    }

    public Long getProjectId() {
        return projectId;
    }

    public String getProjectName() {
        return projectName;
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

    public void setProjectType(ProjectType projectType) {
        this.projectType = projectType;
    }

    public User getOwner() {
        return owner;
    }

    public Project setOwner(User owner) {
        this.owner = owner;
        return this;
    }

    public ProjectContent getContent() {
        return content;
    }

    public Project setContent(ProjectContent content) {
        this.content = content;
        return this;
    }

    public List<ProjectImage> getImages() {
        return (images == null) ? new ArrayList<>() : images;
    }

    public Project setImages(List<ProjectImage> images) {
        this.images = images;
        return this;
    }
}
