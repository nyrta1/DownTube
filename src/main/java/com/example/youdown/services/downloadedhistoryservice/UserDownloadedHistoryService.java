package com.example.youdown.services.downloadedhistoryservice;

import com.example.youdown.models.MediaFile;
import com.example.youdown.models.UserEntity;

public interface DownloadedHistoryService {
    void saveToUserHistory(UserEntity userEntity, MediaFile response);
}
