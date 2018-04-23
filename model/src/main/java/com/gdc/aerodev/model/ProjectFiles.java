package com.gdc.aerodev.model;

import java.util.List;

/**
 * This sub entity contains an array of any project files
 *
 * @see Project
 * @author Yusupov Danil
 */
public class ProjectFiles {

    /**
     * This is {@code FOREIGN KEY} to {@code Project} entity.
     */
    private Long projectId;

    /**
     * This is an array of project's files
     */
    private List<byte[]> files;

    public ProjectFiles(Long projectId, List<byte[]> files) {
        this.projectId = projectId;
        this.files = files;
    }

    public ProjectFiles(List<byte[]> files) {
        this.files = files;
    }

    public Long getProjectId() {
        return projectId;
    }

    public List<byte[]> getFiles() {
        return files;
    }
}
