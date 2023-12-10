package com.example.youdown.models;

import jakarta.persistence.*;

@Entity
public class VideoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int videoID;

    private String path;
    private String quality;

    @ManyToOne
    @JoinColumn(name = "formatID")
    private FormatEntity formatEntity;
}