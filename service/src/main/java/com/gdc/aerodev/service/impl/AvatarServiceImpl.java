package com.gdc.aerodev.service.impl;

import com.gdc.aerodev.dao.AvatarDao;
import com.gdc.aerodev.dao.UserDao;
import com.gdc.aerodev.dao.postgres.PostgresAvatarDao;
import com.gdc.aerodev.dao.postgres.PostgresUserDao;
import com.gdc.aerodev.model.Avatar;
import com.gdc.aerodev.service.AvatarService;
import org.springframework.stereotype.Service;

/**
 * Implementation of service for managing user's avatars in database
 *
 * @author Yusupov Danil
 * @see AvatarService
 * @see com.gdc.aerodev.model.User
 * @see Avatar
 */
@Service
public class AvatarServiceImpl implements AvatarService {
    /**
     * DAO for managing {@code Avatar}
     */
    private final AvatarDao avDao;

    /**
     * DAO for managing {@code User} especially {@code userId}
     */
    private final UserDao usrDao;

    /**
     * Id of default avatar for male user
     */
    private final Long DEFAULT_MAN_AVATAR = 1L;

    /**
     * Id of default avatar for female user
     */
    private final Long DEFAULT_WOMAN_AVATAR = 2L;

    public AvatarServiceImpl(PostgresAvatarDao avDao, PostgresUserDao usrDao) {
        this.avDao = avDao;
        this.usrDao = usrDao;
    }

    /**
     * Checks existence of avatar by {@code User} id
     *
     * @param userId of user
     * @return (0) true if exists or
     * (1) false if not exists
     */
    public boolean isExistent(Long userId) {
        return avDao.getById(userId) != null;
    }

    @Override
    public Avatar getAvatar(Long userId) {
        Avatar avatar = avDao.getById(userId);
        if (avatar == null) {
            if (usrDao.getById(userId).isMale()) {
                return avDao.getById(DEFAULT_MAN_AVATAR);
            } else {
                return avDao.getById(DEFAULT_WOMAN_AVATAR);
            }
        } else {
            return avatar;
        }
    }

    @Override
    public Long uploadAvatar(Long userId, byte[] bytes, String contentType) {
        try {
            Long avatarId = avDao.getById(userId).getAvatarId();
            return avDao.save(new Avatar(avatarId, userId, bytes, contentType));
        } catch (NullPointerException e) {
            return avDao.save(new Avatar(userId, bytes, contentType));
        }
    }
}

