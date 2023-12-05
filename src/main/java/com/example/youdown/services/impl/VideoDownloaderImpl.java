package com.example.youdown.services.impl;

import com.example.youdown.models.ContainerData;
import com.example.youdown.services.VideoDownloader;
import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.downloader.request.RequestPlaylistInfo;
import com.github.kiulian.downloader.downloader.request.RequestVideoInfo;
import com.github.kiulian.downloader.downloader.response.Response;
import com.github.kiulian.downloader.model.playlist.PlaylistDetails;
import com.github.kiulian.downloader.model.playlist.PlaylistInfo;
import com.github.kiulian.downloader.model.playlist.PlaylistVideoDetails;
import com.github.kiulian.downloader.model.videos.VideoDetails;
import com.github.kiulian.downloader.model.videos.VideoInfo;
import com.github.kiulian.downloader.model.videos.formats.AudioFormat;
import com.github.kiulian.downloader.model.videos.formats.VideoFormat;
import com.github.kiulian.downloader.model.videos.formats.VideoWithAudioFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class VideoDownloaderImpl implements VideoDownloader {
    @Override
    public List<VideoWithAudioFormat> download(final String videoId) {
        YoutubeDownloader youtubeDownloader = new YoutubeDownloader();

        RequestVideoInfo request = new RequestVideoInfo(videoId);
        Response<VideoInfo> response = youtubeDownloader.getVideoInfo(request);
        VideoInfo video = response.data();

        List<VideoWithAudioFormat> videoFormats = video.videoWithAudioFormats();

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

    @Override
    public VideoDetails getFullInfo(String videoId) {
        YoutubeDownloader youtubeDownloader = new YoutubeDownloader();

        RequestVideoInfo request = new RequestVideoInfo(videoId);
        Response<VideoInfo> response = youtubeDownloader.getVideoInfo(request);
        VideoInfo video = response.data();

        VideoDetails details = video.details();

        return details;
    }

    @Override
    public ContainerData getAllData(String videoId) {
        log.info("Fetching all data from the library for video ID: {}", videoId);
        YoutubeDownloader youtubeDownloader = new YoutubeDownloader();

        RequestVideoInfo request = new RequestVideoInfo(videoId);
        Response<VideoInfo> response = youtubeDownloader.getVideoInfo(request);

        if (!response.ok()) {
            return null;
        }

        VideoInfo video = response.data();

        List<VideoWithAudioFormat> videoWithAudioFormatList = video.videoWithAudioFormats();
        List<VideoFormat> videoFormatList = video.videoFormats();
        List<AudioFormat> audioFormats = video.audioFormats();
        VideoDetails videoDetails = video.details();

        ContainerData containerData = new ContainerData(videoWithAudioFormatList,videoFormatList, audioFormats, videoDetails);
        return containerData;
    }

    @Override
    public ContainerData getPlaylist(String videoId) {
        YoutubeDownloader youtubeDownloader = new YoutubeDownloader();

        RequestPlaylistInfo request = new RequestPlaylistInfo(videoId);
        Response<PlaylistInfo> response = youtubeDownloader.getPlaylistInfo(request);

        if (!response.ok()) {
            return null;
        }

        PlaylistInfo playlistInfo = response.data();
        PlaylistDetails details = playlistInfo.details();
        List<PlaylistVideoDetails> list = playlistInfo.videos();

        ContainerData containerData = new ContainerData(list, details);

        return containerData;
    }
}
