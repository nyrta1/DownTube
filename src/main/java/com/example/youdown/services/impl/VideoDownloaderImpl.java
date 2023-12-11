package com.example.youdown.services.impl;

import com.example.youdown.models.ContainerData;
import com.example.youdown.services.VideoDownloader;
import com.example.youdown.storage.HashRamMemory;
import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.downloader.request.RequestChannelUploads;
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
    public ContainerData getAllData(String videoId) {
        if (videoId == null) {
            return null;
        }

        ContainerData dataFromLocalStorage = HashRamMemory.getInstance().getData(videoId);
        if (dataFromLocalStorage != null) {
            return dataFromLocalStorage;
        }

        log.info("Fetching all data from the library for dataID: {}", videoId);

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
        HashRamMemory.getInstance().saveData(videoId, containerData);

        return containerData;
    }

    @Override
    public ContainerData getPlaylist(String playlistId) {
        if (playlistId == null) {
            return null;
        }

        ContainerData dataFromLocalStorage = HashRamMemory.getInstance().getData(playlistId);
        if (dataFromLocalStorage != null) {
            return dataFromLocalStorage;
        }

        YoutubeDownloader youtubeDownloader = new YoutubeDownloader();

        RequestPlaylistInfo request = new RequestPlaylistInfo(playlistId);
        Response<PlaylistInfo> response = youtubeDownloader.getPlaylistInfo(request);

        if (!response.ok()) {
            return null;
        }

        PlaylistInfo playlistInfo = response.data();
        PlaylistDetails details = playlistInfo.details();
        List<PlaylistVideoDetails> list = playlistInfo.videos();

        ContainerData containerData = new ContainerData(list, details);
        HashRamMemory.getInstance().saveData(playlistId, containerData);

        return containerData;
    }

    @Override
    public ContainerData getChannelInfo(String channelId) {
        if (channelId == null) {
            return null;
        }

        ContainerData dataFromLocalStorage = HashRamMemory.getInstance().getData(channelId);

        if (dataFromLocalStorage != null) {
            return dataFromLocalStorage;
        }

        YoutubeDownloader youtubeDownloader = new YoutubeDownloader();

        RequestChannelUploads request = new RequestChannelUploads(channelId);
        Response<PlaylistInfo> response = youtubeDownloader.getChannelUploads(request);

        if (!response.ok()) {
            return null;
        }

        PlaylistInfo playlistInfo = response.data();

        PlaylistDetails details = playlistInfo.details();
        List<PlaylistVideoDetails> playlistVideoDetailsList = playlistInfo.videos();

        ContainerData containerData = new ContainerData(playlistVideoDetailsList, details);
        HashRamMemory.getInstance().saveData(channelId, containerData);

        return containerData;
    }
}
