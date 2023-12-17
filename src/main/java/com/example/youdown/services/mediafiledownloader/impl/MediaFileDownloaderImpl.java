package com.example.youdown.services.mediafiledownloader.impl;

import com.example.youdown.constants.Constants;
import com.example.youdown.enums.IndexingFormat;
import com.example.youdown.merger.FFmpegAudioVideoMerger;
import com.example.youdown.models.ContainerData;
import com.example.youdown.services.mediafiledownloader.MediaFileDownloader;
import com.example.youdown.services.videodownloader.VideoDownloader;
import com.example.youdown.storage.FileFinder;
import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.downloader.YoutubeProgressCallback;
import com.github.kiulian.downloader.downloader.request.RequestVideoFileDownload;
import com.github.kiulian.downloader.downloader.response.Response;
import com.github.kiulian.downloader.model.Extension;
import com.github.kiulian.downloader.model.videos.formats.AudioFormat;
import com.github.kiulian.downloader.model.videos.formats.Format;
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
    private final VideoDownloader videoDownloader;

    @Autowired
    public MediaFileDownloaderImpl(VideoDownloader videoDownloader) {
        this.videoDownloader = videoDownloader;
    }

    @Override
    public File download(String dataId, String quality, String format, IndexingFormat indexingFormat) {
        String fileName = buildFileName(indexingFormat.getFormatCode(), dataId, quality, format);

        ContainerData containerData = videoDownloader.getAllData(dataId);

        switch (indexingFormat) {
            case AUDIO -> {
                return downloadAudio(containerData, quality, format, fileName);
            }
            case VIDEO -> {
                return downloadVideo(containerData, quality, format, fileName);
            }
            case VIDEO_WITH_AUDIO -> {
                return downloadVideoWithAudio(containerData, quality, format, fileName);
            }
            case MERGED_AUDIO_WITH_VIDEO -> {
                return downloadMergedAudioWithVideo(containerData, dataId, quality, format, fileName);
            }
        }

        return null;
    }

    private File downloadAudio(ContainerData containerData, String quality, String format, String fileName) {
        AudioFormat audioFormat = getAudioFormat(containerData, quality, format);
        return downloadRequest(audioFormat, fileName);
    }

    private File downloadVideo(ContainerData containerData, String quality, String format, String fileName) {
        VideoFormat videoFormat = getVideoFormat(containerData, quality, format);
        return downloadRequest(videoFormat, fileName);
    }

    private File downloadVideoWithAudio(ContainerData containerData, String quality, String format, String fileName) {
        VideoWithAudioFormat videoWithAudioFormat = getVideoWithAudioFormat(containerData, quality, format);
        return downloadRequest(videoWithAudioFormat, fileName);
    }

    private File downloadMergedAudioWithVideo(ContainerData containerData, String dataId, String quality, String format, String fileName) {
        String audioQualityName = AudioQuality.medium.name();
        String extensionName = Extension.M4A.value();

        AudioFormat audioFormat = getAudioFormat(containerData, audioQualityName, extensionName);
        VideoFormat videoFormat = getVideoFormat(containerData, quality, format);

        String audioFileName = buildFileName(IndexingFormat.AUDIO.getFormatCode(), dataId, audioQualityName, extensionName);
        String videoFileName = buildFileName(IndexingFormat.VIDEO.getFormatCode(), dataId, quality, format);

        return downloadMergedRequest(audioFormat, videoFormat, audioFileName, videoFileName, fileName);
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


    private File downloadRequest(Format format, String fileName) {
        try {
            File fileFromPackageStorage = getFileFromPackageStorage(fileName);
            if (fileFromPackageStorage != null) {
                return fileFromPackageStorage;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (format != null) {
            return downloadVideo(format, fileName);
        } else {
            logErrorFormatNotFound();
            return null;
        }
    }

    private File getFileFromPackageStorage(String fileName) throws IOException {
        return FileFinder.findFileInDirectory(Constants.directoryPathForMediaPackage, fileName);
    }

    private File downloadVideo(Format format, String fileName) {
        YoutubeDownloader youtubeDownloader = new YoutubeDownloader();
        RequestVideoFileDownload request = createVideoDownloadRequest(format, fileName);
        Response<File> response = youtubeDownloader.downloadVideoFile(request);
        return response.data();
    }

    private RequestVideoFileDownload createVideoDownloadRequest(Format format, String fileName) {
        return new RequestVideoFileDownload(format)
                .saveTo(new File("media"))
                .renameTo(fileName)
                .overwriteIfExists(false)
                .callback(createVideoDownloadCallback())
                .async();
    }

    private YoutubeProgressCallback<File> createVideoDownloadCallback() {
        return new YoutubeProgressCallback<File>() {
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
        };
    }

    private void logErrorFormatNotFound() {
        log.error("Format meeting criteria not found");
    }

    private File downloadMergedRequest(AudioFormat audioFormat, VideoFormat videoFormat, String audioFileName, String videoFileName, String mergedFileName) {
        File downloadedAudioFile = downloadRequest(audioFormat, audioFileName);
        File downloadedVideoFile = downloadRequest(videoFormat, videoFileName);

        try {
            File fileFromPackageStorage = FileFinder.findFileInDirectory(Constants.directoryPathForMediaPackage, mergedFileName);
            if (fileFromPackageStorage != null) {
                return fileFromPackageStorage;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
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
