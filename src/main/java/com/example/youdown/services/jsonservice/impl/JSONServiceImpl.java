package com.example.youdown.services.jsonservice.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.youdown.models.ContainerData;
import com.example.youdown.models.enums.RequestData;
import com.example.youdown.services.jsonservice.JSONConverter;
import com.example.youdown.services.jsonservice.JSONService;
import com.example.youdown.services.youtubedownloaderservice.CustomYoutubeDownloader;
import com.example.youdown.storage.HashRamMemory;
import com.github.kiulian.downloader.downloader.response.ResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JSONServiceImpl implements JSONService {
    private CustomYoutubeDownloader youtubeDownloader;

    @Autowired
    public JSONServiceImpl(CustomYoutubeDownloader youtubeDownloader) {
        this.youtubeDownloader = youtubeDownloader;
    }

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

        ContainerData containerData = switch (typeRequest) {
            case ALL -> youtubeDownloader.parseVideoRequest(requestId);
            case PLAYLIST -> youtubeDownloader.parsePlaylistRequest(requestId);
            case CHANNEL -> youtubeDownloader.parseChannelRequest(requestId);
        };

        if (containerData.getResponseStatus() == ResponseStatus.completed) {
            HashRamMemory.getInstance().saveData(requestId, containerData);
        }

        return JSONConverter.containerDataToJSON(containerData);
    }
}
