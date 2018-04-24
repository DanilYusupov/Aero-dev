package com.gdc.aerodev.service.impl;

import com.gdc.aerodev.dao.ProjectImageDao;
import com.gdc.aerodev.dao.postgres.PostgresProjectImageDao;
import com.gdc.aerodev.model.ProjectImage;
import com.gdc.aerodev.service.ProjectImageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectImageServiceImpl implements ProjectImageService {

    private ProjectImageDao imageDao;

    public ProjectImageServiceImpl(PostgresProjectImageDao imageDao) {
        this.imageDao = imageDao;
    }

    @Override
    public Long createImage(Long projectId, byte[] image, String contentType) {
        if (image.length == 0 || contentType.equals("")){
            return null;
        }
        return imageDao.save(new ProjectImage(projectId, image, contentType));
    }

    @Override
    public boolean deleteImage(Long imageId) {
        return imageDao.delete(imageId);
    }

    @Override
    public List<ProjectImage> getAll(Long projectId) {
        return imageDao.getAll(projectId);
    }

    @Override
    public ProjectImage get(Long imageId) {
        return imageDao.getById(imageId);
    }
}
