package com.gdc.aerodev.repository.postgresql;

import com.gdc.aerodev.model.User;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface UserRepository extends Repository<User, Long> {
    List<User> findAll();
    User findByUserName(String userName);
    User findByUserId(Long userId);
    User save(User user);
    void deleteByUserId(Long userId);
}
