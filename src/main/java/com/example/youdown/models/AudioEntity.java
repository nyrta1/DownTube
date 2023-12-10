package com.example.youdown.models;

import jakarta.persistence.*;

@Entity
public class AudioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int audioID;

    private String path;
    private String quality;

    @ManyToOne
    @JoinColumn(name = "formatID")
    private FormatEntity formatEntity;
}