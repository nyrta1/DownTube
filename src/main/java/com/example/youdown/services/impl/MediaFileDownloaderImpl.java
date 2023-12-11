package com.example.youdown.services.impl;

import com.example.youdown.constants.Constants;
import com.example.youdown.enums.IndexingFormat;
import com.example.youdown.models.ContainerData;
import com.example.youdown.services.MediaFileDownloader;
import com.example.youdown.services.VideoDownloader;
import com.example.youdown.storage.FileFinder;
import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.downloader.YoutubeProgressCallback;
import com.github.kiulian.downloader.downloader.request.RequestVideoFileDownload;
import com.github.kiulian.downloader.downloader.response.Response;
import com.github.kiulian.downloader.model.videos.formats.AudioFormat;
import com.github.kiulian.downloader.model.videos.formats.Format;
import com.github.kiulian.downloader.model.videos.formats.VideoFormat;
import com.github.kiulian.downloader.model.videos.formats.VideoWithAudioFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
@Slf4j
public class MediaFileDownloaderImpl implements MediaFileDownloader {
    private final VideoDownloader videoDownloader;
    private final YoutubeDownloader youtubeDownloader;

    @Autowired
    public MediaFileDownloaderImpl(VideoDownloader videoDownloader) {
        this.videoDownloader = videoDownloader;
        this.youtubeDownloader = new YoutubeDownloader();
    }

    @Override
    public File download(String dataId, String quality, String format, IndexingFormat indexingFormat) {
        String fileName = buildFileName(indexingFormat.getFormatCode(), dataId, quality, format);

        try {
            File fileFromPackageStorage = FileFinder.findFileInDirectory(Constants.directoryPathForMediaPackage, fileName);
            if (fileFromPackageStorage != null) {
                return fileFromPackageStorage;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ContainerData containerData = videoDownloader.getAllData(dataId);

        switch (indexingFormat) {
            case AUDIO -> {
                AudioFormat audioFormat = containerData.getAudioFormats().stream()
                        .filter(audio -> audio.audioQuality().name().equals(quality) &&
                                audio.extension().value().equals(format))
                        .findFirst()
                        .orElse(null);

                return downloadRequest(audioFormat, fileName);
            }
            case VIDEO -> {
                VideoFormat videoFormat = containerData.getVideoFormats().stream()
                        .filter(video -> video.qualityLabel().equals(quality) &&
                                video.extension().value().equals(format))
                        .findFirst()
                        .orElse(null);

                return downloadRequest(videoFormat, fileName);
            }
            case VIDEO_WITH_AUDIO -> {
                VideoWithAudioFormat videoWithAudioFormat = containerData.getVideoWithAudioFormats().stream()
                        .filter(videoWithAudio -> videoWithAudio.qualityLabel().equals(quality) &&
                                videoWithAudio.extension().value().equals(format))
                        .findFirst()
                        .orElse(null);

                return downloadRequest(videoWithAudioFormat, fileName);
            }
        }

        return null;
    }

    private File downloadRequest(Format format, String fileName) {
        if (format != null) {
            RequestVideoFileDownload request = new RequestVideoFileDownload(format)
                    .saveTo(new File("media"))
                    .renameTo(fileName)
                    .overwriteIfExists(false)
                    .callback(new YoutubeProgressCallback<File>() {
                        @Override
                        public void onDownloading(int progress) {
                            System.out.printf("Downloaded %d%%\n", progress);
                        }

                        @Override
                        public void onFinished(File videoInfo) {
                            System.out.println("Finished file: " + videoInfo);
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            System.out.println("Error: " + throwable.getLocalizedMessage());
                        }
                    }).async();
            Response<File> response = youtubeDownloader.downloadVideoFile(request);
            return response.data();
        } else {
            log.error("Format meeting criteria not found");
            return null;
        }
    }

    private String buildFileName(String indexingFormat, String dataId, String quality, String format) {
        return indexingFormat + "_" + dataId + "_" + quality +  "_" + format;
    }
}
