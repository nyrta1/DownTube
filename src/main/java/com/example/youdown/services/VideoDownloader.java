package com.example.youdown.services;

import com.github.kiulian.downloader.model.videos.formats.VideoFormat;

import java.util.List;

public interface VideoDownloader {
    List<VideoFormat> download(String videoId);
    String getImage(String videoId);
    String getTitle(String videoId);
}
