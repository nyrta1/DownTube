package com.example.youdown.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.youdown.enums.RequestData;
import com.example.youdown.extractors.YouTubeLinkExtractor;
import com.example.youdown.services.jsonservice.JSONService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/json/downloader/")
@Slf4j
public class JSONController {
    private final JSONService jsonService;

    @Autowired
    public JSONController(JSONService jsonService) {
        this.jsonService = jsonService;
    }

    @GetMapping("all")
    public ResponseEntity<JSONObject> getAllData(@RequestParam("videoUrl") String videoUrl) {
        String videoID = YouTubeLinkExtractor.extractVideoId(videoUrl);

        JSONObject jsonObject = jsonService.getJsonData(videoID, RequestData.ALL);

        return new ResponseEntity<>(jsonObject, HttpStatus.OK);
    }

    @GetMapping("playlist")
    public ResponseEntity<JSONObject> getPlaylistData(@RequestParam("playlistUrl") String playlistUrl) {
        String playlistId = YouTubeLinkExtractor.extractPlaylistId(playlistUrl);

        JSONObject jsonObject = jsonService.getJsonData(playlistId, RequestData.PLAYLIST);

        return new ResponseEntity<>(jsonObject, HttpStatus.OK);
    }

    @GetMapping("channel")
    public ResponseEntity<JSONObject> getChannelData(@RequestParam("channelUrl") String channelUrl) {
        String channelId = YouTubeLinkExtractor.extractChannelId(channelUrl);

        JSONObject jsonObject = jsonService.getJsonData(channelId, RequestData.CHANNEL);

        return new ResponseEntity<>(jsonObject, HttpStatus.OK);
    }
}
