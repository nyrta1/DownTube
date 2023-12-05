package com.example.youdown.services;

import com.example.youdown.models.ContainerData;

public interface VideoDownloader {
    ContainerData getAllData(final String videoId);
    ContainerData getPlaylist(final String videoId);
}
