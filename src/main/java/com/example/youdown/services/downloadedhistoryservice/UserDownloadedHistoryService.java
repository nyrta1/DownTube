package com.example.youdown.services.downloadedhistoryservice;

import com.example.youdown.models.MediaFile;

public interface UserDownloadedHistoryService {
    void saveToUserHistory(MediaFile response);
}
