package com.gdc.aerodev.repository;

import com.gdc.aerodev.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackageClasses = User.class)
public class RepoApplication {
    public static void main(String[] args) {
        SpringApplication.run(RepoApplication.class, args);
    }
}
