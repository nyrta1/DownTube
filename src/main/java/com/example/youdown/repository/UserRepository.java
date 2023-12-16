package com.example.youdown.repository;

import com.example.youdown.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findAll();
    Optional<UserEntity> findByUsername(String username);
}
