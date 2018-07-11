package com.gdc.aerodev.repository.postgresql;

import com.gdc.aerodev.model.Project;
import com.gdc.aerodev.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Project findByProjectId(Long id);

    Project findByProjectName(String name);

    List<Project> findAllByOwner(User user);

}
