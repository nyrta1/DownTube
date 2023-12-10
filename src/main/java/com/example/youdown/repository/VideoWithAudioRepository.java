package com.example.youdown.repository;

import com.example.youdown.models.VideoWithAudioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoWithAudioRepository extends JpaRepository<VideoWithAudioEntity, Long> {
}
