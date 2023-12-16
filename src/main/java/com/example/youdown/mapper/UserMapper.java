package com.example.youdown.mapper;

import com.example.youdown.dto.UserDTO;
import com.example.youdown.enums.Role;
import com.example.youdown.models.UserEntity;

public class UserMapper {
    public static UserEntity mapToUser(UserDTO userDTO) {
        UserEntity user = new UserEntity();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.getRoles().add(Role.USER);

        return user;
    }

    public static UserDTO mapToUserDtO(UserEntity user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());

        return userDTO;
    }
}
