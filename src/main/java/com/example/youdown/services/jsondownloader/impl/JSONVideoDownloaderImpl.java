package com.example.youdown.services.jsondownloader.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.youdown.converters.JSONConverter;
import com.example.youdown.enums.RequestData;
import com.example.youdown.models.ContainerData;
import com.example.youdown.services.jsondownloader.JSONVideoDownloader;
import com.example.youdown.storage.HashRamMemory;
import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.downloader.request.RequestPlaylistInfo;
import com.github.kiulian.downloader.downloader.request.RequestVideoInfo;
import com.github.kiulian.downloader.downloader.response.Response;
import com.github.kiulian.downloader.downloader.response.ResponseStatus;
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
public class JSONVideoDownloaderImpl implements JSONVideoDownloader {

    @Override
    public JSONObject getJsonData(String requestId, RequestData typeRequest) {
        if (requestId == null) {
            return null;
        }

        ContainerData dataFromLocalStorage = HashRamMemory.getInstance().getData(requestId);
        if (dataFromLocalStorage != null) {
            return JSONConverter.containerDataToJSON(dataFromLocalStorage);
        }

        log.info("Fetching all data from the library for dataID: {}", requestId);

        YoutubeDownloader youtubeDownloader = new YoutubeDownloader();

        ContainerData containerData = switch (typeRequest) {
            case ALL -> handleVideoRequest(requestId, youtubeDownloader);
            case PLAYLIST -> handlePlaylistRequest(requestId, youtubeDownloader);
        };

        if (containerData.getResponseStatus() == ResponseStatus.completed) {
            HashRamMemory.getInstance().saveData(requestId, containerData);
        }

        return JSONConverter.containerDataToJSON(containerData);
    }

    private ContainerData handleVideoRequest(String requestId, YoutubeDownloader youtubeDownloader) {
        RequestVideoInfo request = new RequestVideoInfo(requestId);
        Response<VideoInfo> response = youtubeDownloader.getVideoInfo(request);
        ResponseStatus responseStatus = response.status();

        if (!response.ok()) {
            return new ContainerData(responseStatus);
        }

        VideoInfo video = response.data();
        List<VideoWithAudioFormat> videoWithAudioFormatList = video.videoWithAudioFormats();
        List<VideoFormat> videoFormatList = video.videoFormats();
        List<AudioFormat> audioFormats = video.audioFormats();
        VideoDetails videoDetails = video.details();

        return new ContainerData(videoWithAudioFormatList, videoFormatList, audioFormats, videoDetails, responseStatus);
    }

    private ContainerData handlePlaylistRequest(String requestId, YoutubeDownloader youtubeDownloader) {
        RequestPlaylistInfo request = new RequestPlaylistInfo(requestId);
        Response<PlaylistInfo> response = youtubeDownloader.getPlaylistInfo(request);
        ResponseStatus responseStatus = response.status();

        if (!response.ok()) {
            return new ContainerData(responseStatus);
        }

        PlaylistInfo playlistInfo = response.data();
        PlaylistDetails details = playlistInfo.details();
        List<PlaylistVideoDetails> list = playlistInfo.videos();

        return new ContainerData(list, details, responseStatus);
    }
}
