package com.gdc.aerodev.model;

/**
 * This sub entity contains an array of any project file
 *
 * @see Project
 * @author Yusupov Danil
 */
public class ProjectFile {

    /**
     * This is {@code FOREIGN KEY} to {@code Project} entity.
     */
    private Long projectId;

    /**
     * This is an array of project's file
     */
    private byte[] file;
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
