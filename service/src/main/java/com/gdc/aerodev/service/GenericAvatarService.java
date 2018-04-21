package com.gdc.aerodev.service;

import com.gdc.aerodev.model.Avatar;

/**
 * Generic interface of service, which works with {@code Avatar} entities
 *
 * @see com.gdc.aerodev.model.Avatar
 * @see com.gdc.aerodev.dao.AvatarDao
 * @author Yusupov Danil
 */
public interface GenericAvatarService {

    /**
     * Inserts avatar into database
     * @param userId owner
     * @param bytes data
     * @param contentType mime type
     * @return id of created/updated avatar
     */
    Long uploadAvatar(Long userId, byte[] bytes, String contentType);

    /**
     * Gives {@code Avatar} by {@code User} id
     * @param id of avatar's owner
     * @return (0) {@code Avatar} or
     *         (1) {@code null}
     */
    Avatar getAvatar(Long id);
}
