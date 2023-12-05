package com.example.youdown.controller;

import com.example.youdown.extractors.YouTubeLinkExtractor;
import com.example.youdown.models.ContainerData;
import com.example.youdown.services.VideoDownloader;
import com.example.youdown.storage.HashRamMemory;
import com.github.kiulian.downloader.model.videos.VideoDetails;
import com.github.kiulian.downloader.model.videos.formats.VideoFormat;
import com.github.kiulian.downloader.model.videos.formats.VideoWithAudioFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

        ContainerData data = HashRamMemory.getInstance().getData(videoID);

        if (data == null || data.isEmpty()) {
            data = videoDownloader.getAllData(videoID);

            // If the data is ever empty, we will send the user to a home page
            if (data == null || data.isEmpty()) {
                model.addAttribute("error", "Oops! It seems like the provided link is incorrect. Please check and try again.");
                return "home";
            }

            HashRamMemory.getInstance().saveData(videoID, data);
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

        ContainerData data = HashRamMemory.getInstance().getData(playlistId);

        if (data == null) {
            data = videoDownloader.getPlaylist(playlistId);

            // If the data is ever empty, we will send the user to a home page
            if (data == null) {
                model.addAttribute("error", "Oops! It seems like the provided link is incorrect. Please check and try again.");
                return "home-playlist";
            }

            HashRamMemory.getInstance().saveData(playlistId, data);
        }

        model.addAttribute("playlistVideoDetails", data.getPlaylistVideoDetails());
        model.addAttribute("details", data.getPlaylistDetails());

        return "playlist-downloader";
    }

}
