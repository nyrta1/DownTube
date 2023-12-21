package com.example.youdown.services.youtubedownloaderservice;

import com.example.youdown.models.ContainerData;
import com.github.kiulian.downloader.model.videos.formats.AudioFormat;
import com.github.kiulian.downloader.model.videos.formats.Format;
import com.github.kiulian.downloader.model.videos.formats.VideoFormat;

import java.io.File;

public interface CustomYoutubeDownloader {
    File download(Format format, String fileName);
    File mergedVideo(AudioFormat audioFormat, VideoFormat videoFormat, String audioFileName, String videoFileName, String mergedFileName);
    ContainerData parseVideoRequest(String requestId);
    ContainerData parsePlaylistRequest(String requestId);
    ContainerData parseChannelRequest(String requestId);
}
