package com.example.youdown.services.userservice;

import com.example.youdown.models.UserEntity;

import java.util.Optional;

public interface UserService {
    void saveUser(UserEntity registerUser);
    Optional<UserEntity> findUser(UserEntity user);
}
