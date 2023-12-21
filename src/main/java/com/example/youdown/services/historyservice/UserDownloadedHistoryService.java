package com.example.youdown.services.historyservice;

import com.example.youdown.models.MediaFile;

public interface UserDownloadedHistoryService {
    void saveToUserHistory(MediaFile response);
}
