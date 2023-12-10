package com.example.youdown.repository;

import com.example.youdown.models.AudioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AudioRepository extends JpaRepository<AudioEntity, Integer> {
    // Custom query methods if needed
}