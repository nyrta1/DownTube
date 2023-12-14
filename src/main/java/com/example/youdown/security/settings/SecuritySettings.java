package com.example.youdown.security.settings;

public class SecuritySettings {
    public final static String[] YOUTUBE_MEDIA_FILE_DOWNLOADER_API = {
            "/media/download/video-with-audio",
            "/media/download/audio",
            "/media/download/video",
            "/media/download/merged-audio-with-video"
    };

    public final static String[] YOUTUBE_PARSED_JSON_DATA_SENDER = {
            "/json/downloader/all",
            "/json/downloader/playlist"
    };

    public final static String[] ALLOW_ERROR_PAGES_LIST = {
            "/error/**"
    };
}

