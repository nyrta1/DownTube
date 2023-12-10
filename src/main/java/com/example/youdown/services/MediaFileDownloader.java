package com.example.youdown.services;

import com.example.youdown.enums.IndexingFormat;
import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.model.videos.formats.AudioFormat;
import com.github.kiulian.downloader.model.videos.formats.Format;
import com.github.kiulian.downloader.model.videos.formats.VideoFormat;
import com.github.kiulian.downloader.model.videos.formats.VideoWithAudioFormat;

import java.io.File;

public interface MediaFileDownloader {
    File download(final String dataId, final String quality, final String format, final IndexingFormat indexingFormat);
}
