package com.example.youdown.mapper;

import com.example.youdown.payload.request.LoginRequest;
import com.example.youdown.models.enums.Role;
import com.example.youdown.models.UserEntity;

public class UserMapper {
    public static UserEntity mapToUser(LoginRequest loginRequest) {
        UserEntity user = new UserEntity();
        user.setUsername(loginRequest.getUsername());
        user.setPassword(loginRequest.getPassword());
        user.getRoles().add(Role.USER);

        return user;
    }

    public static LoginRequest mapToUserDtO(UserEntity user) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(user.getUsername());
        loginRequest.setPassword(user.getPassword());

        return loginRequest;
    }
}
