package com.gdc.aerodev.repository.postgresql;

import com.gdc.aerodev.model.Avatar;
import org.springframework.data.repository.Repository;

public interface AvatarRepository extends Repository<Avatar, Long> {
    Avatar findByAvatarOwner(Long userId);
    Avatar save(Avatar avatar);
    void deleteByAvatarOwner(Long userId);
}