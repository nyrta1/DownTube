package com.example.youdown.models;

import jakarta.persistence.*;

@Entity
public class VideoWithAudioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int videoWithAudioID;

    private String path;
    private String quality;

    @ManyToOne
    @JoinColumn(name = "formatID")
    private FormatEntity formatEntity;
}
