package com.example.youdown.repository;

import com.example.youdown.models.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<VideoEntity, Integer> {

}