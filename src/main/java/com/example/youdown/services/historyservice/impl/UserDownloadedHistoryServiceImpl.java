package com.example.youdown.services.historyservice.impl;

import com.example.youdown.models.MediaFile;
import com.example.youdown.models.UserEntity;
import com.example.youdown.repository.MediaFileRepository;
import com.example.youdown.security.util.SecurityUtil;
import com.example.youdown.services.historyservice.UserDownloadedHistoryService;
import com.example.youdown.services.userservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDownloadedHistoryServiceImpl implements UserDownloadedHistoryService {
    private final UserService userService;
    private final MediaFileRepository mediaFileRepository;

    @Autowired
    public UserDownloadedHistoryServiceImpl(UserService userService, MediaFileRepository mediaFileRepository) {
        this.userService = userService;
        this.mediaFileRepository = mediaFileRepository;
    }

    @Override
    public void saveToUserHistory(MediaFile downloadedFile) {
        mediaFileRepository.save(downloadedFile);
        Optional<UserEntity> currentUser = userService.findUserByUsername(
                SecurityUtil.getSessionUser());

        if (currentUser.isPresent()){
            UserEntity modifiedUser = currentUser.get();
            modifiedUser.getDownloadedMedia().add(downloadedFile);
            userService.saveUser(modifiedUser);
        }
    }
}
