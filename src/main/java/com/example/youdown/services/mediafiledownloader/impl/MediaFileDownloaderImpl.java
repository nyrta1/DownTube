package com.example.youdown.services.mediafiledownloader.impl;

import com.example.youdown.constants.Constants;
import com.example.youdown.merger.FFmpegAudioVideoMerger;
import com.example.youdown.models.ContainerData;
import com.example.youdown.models.MediaFile;
import com.example.youdown.models.enums.FileType;
import com.example.youdown.models.enums.IndexingFormat;
import com.example.youdown.services.historyservice.UserDownloadedHistoryService;
import com.example.youdown.services.mediafiledownloader.MediaFileDownloader;
import com.example.youdown.services.youtubedownloaderservice.CustomYoutubeDownloader;
import com.example.youdown.storage.FileFinder;
import com.example.youdown.storage.HashRamMemory;
import com.github.kiulian.downloader.model.Extension;
import com.github.kiulian.downloader.model.videos.formats.AudioFormat;
import com.github.kiulian.downloader.model.videos.formats.VideoFormat;
import com.github.kiulian.downloader.model.videos.formats.VideoWithAudioFormat;
import com.github.kiulian.downloader.model.videos.quality.AudioQuality;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
@Slf4j
public class MediaFileDownloaderImpl implements MediaFileDownloader {
    private final UserDownloadedHistoryService userDownloadedHistoryService;
    private final CustomYoutubeDownloader youtubeDownloader;

    @Autowired
    public MediaFileDownloaderImpl(UserDownloadedHistoryService userDownloadedHistoryService, CustomYoutubeDownloader youtubeDownloader) {
        this.userDownloadedHistoryService = userDownloadedHistoryService;
        this.youtubeDownloader = youtubeDownloader;
    }

    @Override
    public File download(String dataId, String quality, String format, IndexingFormat indexingFormat) {
        String fileName = buildFileName(indexingFormat.getFormatCode(), dataId, quality, format);
        System.err.println(fileName);

        ContainerData containerData = HashRamMemory.getInstance().getData(dataId);

        File downloadedFile = null;
        FileType fileType = null;
        switch (indexingFormat) {
            case AUDIO -> {
                downloadedFile = downloadAudio(containerData, quality, format, fileName);
                fileType = FileType.AUDIO;
                return downloadedFile;
            }
            case VIDEO -> {
                downloadedFile = downloadVideo(containerData, quality, format, fileName);
                fileType = FileType.VIDEO;
                return downloadedFile;
            }
            case VIDEO_WITH_AUDIO -> {
                downloadedFile = downloadVideoWithAudio(containerData, quality, format, fileName);
                fileType = FileType.VIDEO;
                return downloadedFile;
            }
            case MERGED_AUDIO_WITH_VIDEO -> {
                downloadedFile = downloadMergedAudioWithVideo(containerData, dataId, quality, format, fileName);
                fileType = FileType.VIDEO;
                return downloadedFile;
            }
        }

        if (downloadedFile != null) {
            MediaFile downloadedMediaFile = new MediaFile(
                    null, dataId, downloadedFile.getPath(), null, quality, fileType
            );
            userDownloadedHistoryService.saveToUserHistory(downloadedMediaFile);
        }

        return null;
    }


    private AudioFormat getAudioFormat(ContainerData containerData, String quality, String format) {
        AudioFormat audioFormat = containerData.getAudioFormats().stream()
                .filter(audio -> audio.audioQuality().name().equals(quality) &&
                        audio.extension().value().equals(format))
                .findFirst()
                .orElse(null);

        return audioFormat;
    }

    private VideoFormat getVideoFormat(ContainerData containerData, String quality, String format) {
        VideoFormat videoFormat = containerData.getVideoFormats().stream()
                .filter(video -> video.qualityLabel().equals(quality) &&
                        video.extension().value().equals(format))
                .findFirst()
                .orElse(null);

        return videoFormat;
    }

    private VideoWithAudioFormat getVideoWithAudioFormat(ContainerData containerData, String quality, String format) {
        VideoWithAudioFormat videoWithAudioFormat = containerData.getVideoWithAudioFormats().stream()
                .filter(videoWithAudio -> videoWithAudio.qualityLabel().equals(quality) &&
                        videoWithAudio.extension().value().equals(format))
                .findFirst()
                .orElse(null);

        return videoWithAudioFormat;
    }

    private File downloadAudio(ContainerData containerData, String quality, String format, String fileName) {
        AudioFormat audioFormat = getAudioFormat(containerData, quality, format);
        return youtubeDownloader.download(audioFormat, fileName);
    }

    private File downloadVideo(ContainerData containerData, String quality, String format, String fileName) {
        VideoFormat videoFormat = getVideoFormat(containerData, quality, format);
        return youtubeDownloader.download(videoFormat, fileName);
    }

    private File downloadVideoWithAudio(ContainerData containerData, String quality, String format, String fileName) {
        VideoWithAudioFormat videoWithAudioFormat = getVideoWithAudioFormat(containerData, quality, format);
        return youtubeDownloader.download(videoWithAudioFormat, fileName);
    }

    private File downloadMergedAudioWithVideo(ContainerData containerData, String dataId, String quality, String format, String fileName) {
        String audioQualityName = AudioQuality.medium.name();
        String extensionName = Extension.M4A.value();

        AudioFormat audioFormat = getAudioFormat(containerData, audioQualityName, extensionName);
        VideoFormat videoFormat = getVideoFormat(containerData, quality, format);

        String audioFileName = buildFileName(IndexingFormat.AUDIO.getFormatCode(), dataId, audioQualityName, extensionName);
        String videoFileName = buildFileName(IndexingFormat.VIDEO.getFormatCode(), dataId, quality, format);

        return youtubeDownloader.mergedVideo(audioFormat, videoFormat, audioFileName, videoFileName, fileName);
    }

    private File downloadMergedRequest(AudioFormat audioFormat, VideoFormat videoFormat, String audioFileName, String videoFileName, String mergedFileName) throws IOException {
        File downloadedAudioFile = youtubeDownloader.download(audioFormat, audioFileName);
        File downloadedVideoFile = youtubeDownloader.download(videoFormat, videoFileName);

        File fileFromPackageStorage = FileFinder.findFileInDirectory(Constants.directoryPathForMediaPackage, mergedFileName);
        if (fileFromPackageStorage != null) {
            return fileFromPackageStorage;
        }

        if (downloadedAudioFile != null && downloadedVideoFile != null) {
            File mergedFile = FFmpegAudioVideoMerger.merge(downloadedAudioFile.getPath(), downloadedVideoFile.getPath(), mergedFileName + ".mp4");
            return mergedFile;
        } else {
            log.error("Downloaded files are null");
            return null;
        }
    }

    private String buildFileName(String indexingFormat, String dataId, String quality, String format) {
        return indexingFormat + "_" + dataId + "_" + quality +  "_" + format;
    }
}
