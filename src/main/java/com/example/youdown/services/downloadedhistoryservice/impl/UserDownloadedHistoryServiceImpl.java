package com.example.youdown.services.downloadedhistoryservice.impl;

import com.example.youdown.models.MediaFile;
import com.example.youdown.models.UserEntity;
import com.example.youdown.repository.UserRepository;
import com.example.youdown.services.downloadedhistoryservice.UserDownloadedHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDownloadedHistoryServiceImpl implements UserDownloadedHistoryService {
    private UserRepository userRepository;

    @Autowired
    public UserDownloadedHistoryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveToUserHistory(UserEntity userEntity, MediaFile response) {
        MediaFile mediaFile = new MediaFile();
        userEntity.setDownloadedMedia(List.of());
    }
}
