package com.example.youdown.services;

import com.example.youdown.models.ContainerData;

public interface VideoDownloader {
    // Simple data
    ContainerData getAllData(final String videoId);
    ContainerData getPlaylist(final String playlistId);
    ContainerData getChannelInfo(final String channelId);
}
