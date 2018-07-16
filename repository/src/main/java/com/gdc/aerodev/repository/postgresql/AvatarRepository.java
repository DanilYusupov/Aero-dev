package com.gdc.aerodev.repository.postgresql;

import com.gdc.aerodev.model.Avatar;
import com.gdc.aerodev.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Avatar findByUser(User user);
}
