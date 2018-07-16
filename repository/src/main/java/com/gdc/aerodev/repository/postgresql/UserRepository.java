package com.gdc.aerodev.repository.postgresql;

import com.gdc.aerodev.model.User;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserId(Long id);

    User findByUserName(String userName);

    @Query("SELECT u FROM User u ORDER BY u.userRating DESC")
    List<User> ratingOrdered(Pageable pageable);
}