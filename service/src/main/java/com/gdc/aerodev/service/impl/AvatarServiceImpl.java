package com.gdc.aerodev.service.impl;

import com.gdc.aerodev.model.Avatar;
import com.gdc.aerodev.model.User;
import com.gdc.aerodev.repository.postgresql.AvatarRepository;
import com.gdc.aerodev.repository.postgresql.UserRepository;
import com.gdc.aerodev.service.AvatarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    private final AvatarRepository avatarRepository;

    /**
     * DAO for managing {@code User} especially {@code userId}
     */
    private final UserRepository userRepository;

    /**
     * Id of default avatar for male user
     */
    private final Long DEFAULT_MAN_AVATAR = 1L;

    /**
     * Id of default avatar for female user
     */
    private final Long DEFAULT_WOMAN_AVATAR = 2L;

    @Autowired
    public AvatarServiceImpl(AvatarRepository avatarRepository, UserRepository userRepository) {
        this.avatarRepository = avatarRepository;
        this.userRepository = userRepository;
    }

    /**
     * Checks existence of avatar by {@code User} id
     *
     * @param userId of user
     * @return (0) true if exists or
     * (1) false if not exists
     */
    public boolean isExistent(Long userId) {
        return !avatarRepository.findById(userId).isPresent();
    }

    @Override
    public Avatar getAvatar(Long userId) {
        Optional<Avatar> avatar = avatarRepository.findById(userId);
        User user = userRepository.findByUserId(userId);
        if (!avatar.isPresent()) {
            if (user.isMale()) {
                return avatarRepository.findById(DEFAULT_MAN_AVATAR).get();
            } else {
                return avatarRepository.findById(DEFAULT_WOMAN_AVATAR).get();
            }
        } else {
            return avatar.get();
        }
    }

    @Override
    public Long uploadAvatar(Long userId, byte[] bytes, String contentType) {
        User user = userRepository.findByUserId(userId);
        Long avatarId = avatarRepository.findByUser(user).getAvatarId();
        return avatarRepository.save(new Avatar(user, bytes, contentType)).getAvatarId();
    }
}

