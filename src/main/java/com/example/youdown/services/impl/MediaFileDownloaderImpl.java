package com.example.youdown.services.impl;

import com.example.youdown.constants.Constants;
import com.example.youdown.enums.IndexingFormat;
import com.example.youdown.merger.FFmpegAudioVideoMerger;
import com.example.youdown.models.ContainerData;
import com.example.youdown.services.MediaFileDownloader;
import com.example.youdown.services.VideoDownloader;
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
            case MERGED_AUDIO_WITH_VIDEO -> {
                AudioFormat audioFormat = containerData.getAudioFormats().stream()
                        .filter(audio -> audio.audioQuality().equals(AudioQuality.medium) &&
                                audio.extension().equals(Extension.M4A))
                        .findFirst()
                        .orElse(null);

                String audioFileName = buildFileName(IndexingFormat.AUDIO.getFormatCode(), dataId, AudioQuality.high.name(), Extension.M4A.value());

                VideoFormat videoFormat = containerData.getVideoFormats().stream()
                        .filter(video -> video.qualityLabel().equals(quality) &&
                                video.extension().value().equals(format))
                        .findFirst()
                        .orElse(null);

                String videoFileName = buildFileName(IndexingFormat.VIDEO.getFormatCode(), dataId, quality, format);

                return downloadMergedRequest(audioFormat, videoFormat, audioFileName, videoFileName, fileName);
            }
        }

        return null;
    }

    private File downloadRequest(Format format, String fileName) {
        try {
            File fileFromPackageStorage = FileFinder.findFileInDirectory(Constants.directoryPathForMediaPackage, fileName);
            if (fileFromPackageStorage != null) {
                return fileFromPackageStorage;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (format != null) {
            YoutubeDownloader youtubeDownloader = new YoutubeDownloader();
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
//            Constants.directoryPathForMediaPackage + "/" +
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
