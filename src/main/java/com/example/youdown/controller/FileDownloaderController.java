package com.example.youdown.controller;

import com.example.youdown.enums.IndexingFormat;
import com.example.youdown.services.MediaFileDownloader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.util.Objects;

@Controller
@RequestMapping("/media/download/")
@Slf4j
public class FileDownloaderController {
    private MediaFileDownloader mediaFileDownloader;

    @Autowired
    public FileDownloaderController(MediaFileDownloader mediaFileDownloader) {
        this.mediaFileDownloader = mediaFileDownloader;
    }

    @GetMapping("video-with-audio")
    public ResponseEntity<File> downloadVideoWithAudioFormat(
            @RequestParam(value = "url") String url,
            @RequestParam(value = "quality") String quality,
            @RequestParam(value = "format") String format
    ) {
        File response = mediaFileDownloader.download(url, quality, format, IndexingFormat.VIDEO_WITH_AUDIO);

        if (Objects.isNull(response)){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("audio")
    public ResponseEntity<File> downloadAudioFormat(
            @RequestParam(value = "url") String url,
            @RequestParam(value = "quality") String quality,
            @RequestParam(value = "format") String format
    ) {
        File response = mediaFileDownloader.download(url, quality, format, IndexingFormat.AUDIO);

        if (Objects.isNull(response)){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("video")
    public ResponseEntity<File> downloadVideoFormat(
            @RequestParam(value = "url") String url,
            @RequestParam(value = "quality") String quality,
            @RequestParam(value = "format") String format
    ) {
        File response = mediaFileDownloader.download(url, quality, format, IndexingFormat.VIDEO);

        if (Objects.isNull(response)){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
