package com.example.youdown.services.youtubedownloaderservice.impl;

import com.example.youdown.constants.Constants;
import com.example.youdown.merger.FFmpegAudioVideoMerger;
import com.example.youdown.models.ContainerData;
import com.example.youdown.services.youtubedownloaderservice.CustomYoutubeDownloader;
import com.example.youdown.storage.FileFinder;
import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.downloader.YoutubeProgressCallback;
import com.github.kiulian.downloader.downloader.request.RequestChannelUploads;
import com.github.kiulian.downloader.downloader.request.RequestPlaylistInfo;
import com.github.kiulian.downloader.downloader.request.RequestVideoFileDownload;
import com.github.kiulian.downloader.downloader.request.RequestVideoInfo;
import com.github.kiulian.downloader.downloader.response.Response;
import com.github.kiulian.downloader.downloader.response.ResponseStatus;
import com.github.kiulian.downloader.model.playlist.PlaylistDetails;
import com.github.kiulian.downloader.model.playlist.PlaylistInfo;
import com.github.kiulian.downloader.model.playlist.PlaylistVideoDetails;
import com.github.kiulian.downloader.model.videos.VideoDetails;
import com.github.kiulian.downloader.model.videos.VideoInfo;
import com.github.kiulian.downloader.model.videos.formats.AudioFormat;
import com.github.kiulian.downloader.model.videos.formats.Format;
import com.github.kiulian.downloader.model.videos.formats.VideoFormat;
import com.github.kiulian.downloader.model.videos.formats.VideoWithAudioFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class CustomYoutubeDownloaderImpl implements CustomYoutubeDownloader {
    @Override
    public File download(Format format, String fileName) {
        File fileFromPackageStorage = null;

        try {
            fileFromPackageStorage = getFileFromPackageStorage(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (fileFromPackageStorage != null) {
            return fileFromPackageStorage;
        }

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
                })
                .async();
        Response<File> response = youtubeDownloader.downloadVideoFile(request);
        return response.data();
    }

    @Override
    public ContainerData parseVideoRequest(String requestId) {
        YoutubeDownloader youtubeDownloader = new YoutubeDownloader();

        RequestVideoInfo request = new RequestVideoInfo(requestId);
        Response<VideoInfo> response = youtubeDownloader.getVideoInfo(request);
        ResponseStatus responseStatus = response.status();

        if (!response.ok()) {
            return new ContainerData(responseStatus);
        }

        VideoInfo video = response.data();
        List<VideoWithAudioFormat> videoWithAudioFormatList = video.videoWithAudioFormats();
        List<VideoFormat> videoFormatList = video.videoFormats();
        List<AudioFormat> audioFormats = video.audioFormats();
        VideoDetails videoDetails = video.details();

        return new ContainerData(videoWithAudioFormatList, videoFormatList, audioFormats, videoDetails, responseStatus);
    }

    @Override
    public ContainerData parsePlaylistRequest(String requestId) {
        YoutubeDownloader youtubeDownloader = new YoutubeDownloader();

        RequestPlaylistInfo request = new RequestPlaylistInfo(requestId);
        Response<PlaylistInfo> response = youtubeDownloader.getPlaylistInfo(request);
        ResponseStatus responseStatus = response.status();

        if (!response.ok()) {
            return new ContainerData(responseStatus);
        }

        PlaylistInfo playlistInfo = response.data();
        PlaylistDetails details = playlistInfo.details();
        List<PlaylistVideoDetails> list = playlistInfo.videos();

        return new ContainerData(list, details, responseStatus);
    }

    @Override
    public ContainerData parseChannelRequest(String requestId) {
        YoutubeDownloader youtubeDownloader = new YoutubeDownloader();

        RequestChannelUploads request = new RequestChannelUploads(requestId);
        Response<PlaylistInfo> response = youtubeDownloader.getChannelUploads(request);
        ResponseStatus responseStatus = response.status();

        if (!response.ok()){
            return new ContainerData(responseStatus);
        }

        PlaylistInfo playlistInfo = response.data();

        return new ContainerData(playlistInfo.videos(), playlistInfo.details());
    }

    @Override
    public File mergedVideo(AudioFormat audioFormat, VideoFormat videoFormat, String audioFileName, String videoFileName, String mergedFileName) {
        File downloadedAudioFile = download(audioFormat, audioFileName);
        File downloadedVideoFile = download(videoFormat, videoFileName);

        if (downloadedAudioFile != null && downloadedVideoFile != null) {
            File mergedFile = FFmpegAudioVideoMerger.merge(downloadedAudioFile.getPath(), downloadedVideoFile.getPath(), mergedFileName + ".mp4");
            return mergedFile;
        } else {
            log.error("Downloaded files are null");
            return null;
        }
    }

    private File getFileFromPackageStorage(String fileName) throws IOException {
        return FileFinder.findFileInDirectory(Constants.directoryPathForMediaPackage, fileName);
    }
}
