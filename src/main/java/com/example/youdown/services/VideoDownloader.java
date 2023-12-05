package com.example.youdown.services;

import com.example.youdown.models.ContainerData;
import com.github.kiulian.downloader.model.videos.VideoDetails;
import com.github.kiulian.downloader.model.videos.formats.VideoFormat;
import com.github.kiulian.downloader.model.videos.formats.VideoWithAudioFormat;

import java.util.List;

public interface VideoDownloader {
    List<VideoWithAudioFormat> download(String videoId);
    String getImage(String videoId);
    String getTitle(String videoId);
    VideoDetails getFullInfo(String videoId);
    ContainerData getAllData(final String videoId);
}
