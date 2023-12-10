package com.example.youdown.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class FormatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int formatID;
    private String formatName;
}
