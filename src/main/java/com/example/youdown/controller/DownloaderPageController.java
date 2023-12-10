package com.example.youdown.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.youdown.extractors.YouTubeLinkExtractor;
import com.example.youdown.models.ContainerData;
import com.example.youdown.services.JSONVideoDownloader;
import com.example.youdown.services.VideoDownloader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/downloader/")
@Slf4j
public class DownloaderPageController {
    private final VideoDownloader videoDownloader;

    @Autowired
    public DownloaderPageController(VideoDownloader videoDownloader) {
        this.videoDownloader = videoDownloader;
    }

    @GetMapping("all")
    public String search(@RequestParam(value = "videoUrl") String videoUrl, Model model) {
        log.info("Angular is trying to get data for dataID {}", videoUrl);
        String videoID = YouTubeLinkExtractor.extractVideoId(videoUrl);

        ContainerData data = videoDownloader.getAllData(videoID);

        if (data == null) {
            model.addAttribute("error", "Oops! It seems like the provided link is incorrect. Please check and try again.");
            return "home";
        }

        model.addAttribute("videoFormats", data.getVideoWithAudioFormats());
        model.addAttribute("audioFormats", data.getAudioFormats());
        model.addAttribute("videoNoAudioFormats", data.getVideoFormats());
        model.addAttribute("details", data.getDetails());
        return "downloader";
    }

    @GetMapping("playlist")
    public String playlistSearch(@RequestParam(value = "playlistUrl") String playlistUrl, Model model) {
        String playlistId = YouTubeLinkExtractor.extractPlaylistId(playlistUrl);

        ContainerData data = videoDownloader.getPlaylist(playlistId);

        if (data == null) {
            model.addAttribute("error", "Oops! It seems like the provided link is incorrect. Please check and try again.");
            return "home-playlist";
        }

        model.addAttribute("playlistVideoDetails", data.getPlaylistVideoDetails());
        model.addAttribute("details", data.getPlaylistDetails());

        return "playlist-downloader";
    }

    @GetMapping("channel")
    public String channelSearch(@RequestParam(value = "channelUrl") String channelUrl, Model model) {
        ContainerData data = videoDownloader.getChannelInfo(channelUrl);

        if (data == null) {
            model.addAttribute("error", "Oops! It seems like the provided link is incorrect. Please check and try again.");
            return "home-channel";
        }

        model.addAttribute("playlistVideoDetails", data.getPlaylistVideoDetails());
        model.addAttribute("details", data.getPlaylistDetails());

        return "channel-downloader";
    }
}
