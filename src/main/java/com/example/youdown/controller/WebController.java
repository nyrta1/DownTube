package com.example.youdown.controller;

import com.example.youdown.extractors.YouTubeLinkExtractor;
import com.example.youdown.models.ContainerData;
import com.example.youdown.services.VideoDownloader;
import com.example.youdown.storage.HashRamMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class WebController {
    private final VideoDownloader videoDownloader;

    @Autowired
    public WebController(VideoDownloader videoDownloader) {
        this.videoDownloader = videoDownloader;
    }

    @GetMapping("")
    public String homePage() {
        return "home";
    }

    @GetMapping("search")
    public String search(@RequestParam(value = "videoUrl") String videoUrl, Model model) {
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
    public String playlist(Model model) {
        return "home-playlist";
    }

    @GetMapping("playlist/search")
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

}
