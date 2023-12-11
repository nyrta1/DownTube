package com.example.youdown.services;

import com.example.youdown.enums.IndexingFormat;

import java.io.File;

public interface MediaFileDownloader {
    File download(final String dataId, final String quality, final String format, final IndexingFormat indexingFormat);
}
