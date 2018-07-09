package com.gdc.aerodev.model;

import javax.persistence.*;

/**
 * This sub entity contains an array of any project file
 *
 * @author Yusupov Danil
 * @see Project
 */
@Entity
@Table(name = "project_files")
public class ProjectFile {

    /**
     * This is {@code FOREIGN KEY} to {@code Project} entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prj_id")
    private Long projectId;

    /**
     * This is an array of project's file
     */
    private byte[] file;

    /**
     * MIME type of file
     */
    @Column(name = "content_type")
    private String contentType;

    public ProjectFile(Long projectId, byte[] file, String contentType) {
        this.projectId = projectId;
        this.file = file;
        this.contentType = contentType;
    }

    public ProjectFile(byte[] file) {
        this.file = file;
    }

    public Long getProjectId() {
        return projectId;
    }

    public byte[] getFile() {
        return file;
    }

    public String getContentType() {
        return contentType;
    }
}
