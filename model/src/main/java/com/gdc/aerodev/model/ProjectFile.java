package com.gdc.aerodev.model;

import javax.persistence.*;

/**
 * This sub entity contains an array of any project file
 *
 * @author Yusupov Danil
 * @see Project
 */
@Entity
@Table(schema = "aero", name = "project_files")
public class ProjectFile {

    /**
     * This is {@code FOREIGN KEY} to {@code Project} entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long fileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prj_id", foreignKey = @ForeignKey(name = "file_fk"))
    private Project project;

    /**
     * This is an array of project's file
     */
    private byte[] file;

    /**
     * MIME type of file
     */
    @Column(name = "content_type")
    private String contentType;

    public ProjectFile() {
    }

    public ProjectFile(byte[] file, String contentType) {
        this.file = file;
        this.contentType = contentType;
    }

    public Long getFileId() {
        return fileId;
    }

    public ProjectFile setFileId(Long fileId) {
        this.fileId = fileId;
        return this;
    }

    public Project getProject() {
        return project;
    }

    public ProjectFile setProject(Project project) {
        this.project = project;
        return this;
    }

    public byte[] getFile() {
        return file;
    }

    public ProjectFile setFile(byte[] file) {
        this.file = file;
        return this;
    }

    public String getContentType() {
        return contentType;
    }

    public ProjectFile setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }
}