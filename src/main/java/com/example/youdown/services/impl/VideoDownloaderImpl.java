package com.example.youdown.services.impl;

import com.example.youdown.services.VideoDownloader;
import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.downloader.request.RequestVideoInfo;
import com.github.kiulian.downloader.downloader.response.Response;
import com.github.kiulian.downloader.model.videos.VideoDetails;
import com.github.kiulian.downloader.model.videos.VideoInfo;
import com.github.kiulian.downloader.model.videos.formats.VideoFormat;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoDownloaderImpl implements VideoDownloader {
    @Override
    public List<VideoFormat> download(final String videoId) {
        YoutubeDownloader youtubeDownloader = new YoutubeDownloader();

        RequestVideoInfo request = new RequestVideoInfo(videoId);
        Response<VideoInfo> response = youtubeDownloader.getVideoInfo(request);
        VideoInfo video = response.data();

        List<VideoFormat> videoFormats = video.videoFormats();

        return videoFormats;
    }

    @Override
    public String getImage(String videoId) {
        YoutubeDownloader youtubeDownloader = new YoutubeDownloader();

        RequestVideoInfo request = new RequestVideoInfo(videoId);
        Response<VideoInfo> response = youtubeDownloader.getVideoInfo(request);
        VideoInfo video = response.data();

        VideoDetails details = video.details();

        List<String> thumbnails = details.thumbnails();
        if (!thumbnails.isEmpty()) {
            return thumbnails.get(0);
        }

        return null;
    }

    @Override
    public String getTitle(String videoId) {
        YoutubeDownloader youtubeDownloader = new YoutubeDownloader();

        RequestVideoInfo request = new RequestVideoInfo(videoId);
        Response<VideoInfo> response = youtubeDownloader.getVideoInfo(request);
        VideoInfo video = response.data();

        VideoDetails details = video.details();

        return details.title();
    }
}
