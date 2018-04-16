package com.gdc.aerodev.service.impl;

import com.gdc.aerodev.dao.postgres.PostgresAvatarDao;
import com.gdc.aerodev.model.Avatar;
import com.gdc.aerodev.service.GenericAvatarService;
import com.gdc.aerodev.service.logging.LoggingService;
import org.mockito.internal.matchers.Null;
import org.springframework.stereotype.Service;

@Service
public class AvatarService implements GenericAvatarService, LoggingService {

    private final PostgresAvatarDao avDao;

    public AvatarService(PostgresAvatarDao avDao) {
        this.avDao = avDao;
    }

    /**
     * Checks existence of avatar by {@code User} id
     * @param id of user
     * @return (0) true if exists or
     *         (1) false if not exists
     */
    public boolean isExistent(Long id){
        return avDao.getById(id) != null;
    }


    /**
     * Selects user's avatar from DB
     * @param id of avatar's owner
     * @return (0) {@code Avatar} or
     *         (1) {@code null}
     */
    @Override
    public Avatar getAvatar(Long id) {
        try{
            return avDao.getById(id);
        } catch (NullPointerException e){
            return null;
            //FIXME: return default avatar
        }
    }

    public Long uploadAvatar(Long userId, byte[] bytes, String contentType) {
        try {
            Long avatarId = avDao.getById(userId).getAvatarId();
            return avDao.save(new Avatar(avatarId, userId, bytes, contentType));
        } catch (NullPointerException e){
            return avDao.save(new Avatar(userId, bytes, contentType));
        }
    }
}

