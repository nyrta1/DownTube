package com.example.youdown.services;

import com.example.youdown.models.UserEntity;

import java.util.Optional;

public interface UserService {
    void saveUser(UserEntity registerUser);
    Optional<UserEntity> findUser(UserEntity user);
}
