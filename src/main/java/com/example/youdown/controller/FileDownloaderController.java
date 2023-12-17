package com.example.youdown.controller;

import com.example.youdown.converters.FileConverter;
import com.example.youdown.enums.IndexingFormat;
import com.example.youdown.services.mediafiledownloader.MediaFileDownloader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Controller
@RequestMapping("/media/download/")
@Slf4j
public class FileDownloaderController {
    private final MediaFileDownloader mediaFileDownloader;

    @Autowired
    public FileDownloaderController(MediaFileDownloader mediaFileDownloader) {
        this.mediaFileDownloader = mediaFileDownloader;
    }

    @GetMapping("video-with-audio")
    public ResponseEntity<Resource> downloadVideoWithAudioFormat(
            @RequestParam(value = "url") String url,
            @RequestParam(value = "quality") String quality,
            @RequestParam(value = "format") String format
    ) throws IOException {
        File response = mediaFileDownloader.download(url, quality, format, IndexingFormat.VIDEO_WITH_AUDIO);

        if (Objects.isNull(response)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

//        InputStreamResource resource = new InputStreamResource(new FileInputStream(response));

        byte[] fileContent = FileConverter.convertFileToBytes(response);
        ByteArrayResource resource = new ByteArrayResource(fileContent);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + response.getName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(resource.contentLength())
                .body(resource);
    }

    @GetMapping("audio")
    public ResponseEntity<Resource> downloadAudioFormat(
            @RequestParam(value = "url") String url,
            @RequestParam(value = "quality") String quality,
            @RequestParam(value = "format") String format
    ) throws IOException {
        File response = mediaFileDownloader.download(url, quality, format, IndexingFormat.AUDIO);

        if (Objects.isNull(response)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

//        InputStreamResource resource = new InputStreamResource(new FileInputStream(response));

        byte[] fileContent = FileConverter.convertFileToBytes(response);
        ByteArrayResource resource = new ByteArrayResource(fileContent);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + response.getName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(resource.contentLength())
                .body(resource);
    }

    @GetMapping("video")
    public ResponseEntity<Resource> downloadVideoFormat(
            @RequestParam(value = "url") String url,
            @RequestParam(value = "quality") String quality,
            @RequestParam(value = "format") String format
    ) throws IOException {
        File response = mediaFileDownloader.download(url, quality, format, IndexingFormat.VIDEO);

        if (Objects.isNull(response)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

//        InputStreamResource resource = new InputStreamResource(new FileInputStream(response));

        byte[] fileContent = FileConverter.convertFileToBytes(response);
        ByteArrayResource resource = new ByteArrayResource(fileContent);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + response.getName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(resource.contentLength())
                .body(resource);
    }

    @GetMapping("merged-audio-with-video")
        public ResponseEntity<Resource> downloadAudioVideoMergedFile(
            @RequestParam(value = "url") String url,
            @RequestParam(value = "quality") String quality,
            @RequestParam(value = "format") String format
    ) throws IOException {
        File response = mediaFileDownloader.download(url, quality, format, IndexingFormat.MERGED_AUDIO_WITH_VIDEO);

        if (Objects.isNull(response)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

//        InputStreamResource resource = new InputStreamResource(new FileInputStream(response));

        byte[] fileContent = FileConverter.convertFileToBytes(response);
        ByteArrayResource resource = new ByteArrayResource(fileContent);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + response.getName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(resource.contentLength())
                .body(resource);
    }
}
