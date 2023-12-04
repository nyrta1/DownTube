package com.example.youdown.controller;

import com.example.youdown.services.VideoDownloader;
import com.github.kiulian.downloader.model.videos.formats.VideoFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class WebController {
    private final VideoDownloader videoDownloader;

    @Autowired
    public WebController(VideoDownloader videoDownloader) {
        this.videoDownloader = videoDownloader;
    }

    @GetMapping("/home")
    public String homePage() {
        return "home";
    }

    @GetMapping("/search")
    public String search(@RequestParam(value = "videoUrl") String videoID, Model model) {
        List<VideoFormat> videoFormats = videoDownloader.download(videoID);
        String title = videoDownloader.getTitle(videoID);
        String imageUrl = videoDownloader.getImage(videoID);

        model.addAttribute("videoFormats", videoFormats);
        model.addAttribute("title", title);
        model.addAttribute("imageUrl", imageUrl);

        return "home";
    }
}
