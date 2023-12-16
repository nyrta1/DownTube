package com.example.youdown.controller;

import com.example.youdown.dto.UserDTO;
import com.example.youdown.mapper.UserMapper;
import com.example.youdown.models.UserEntity;
import com.example.youdown.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Controller
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerToSystem(@RequestBody UserDTO userDTO) {
        try {
            UserEntity registerUser = UserMapper.mapToUser(userDTO);
            userService.saveUser(registerUser);
            return new ResponseEntity<>(Map.of("message", "User registered successfully"), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("message", "Registration failed: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginToSystem(@RequestBody UserDTO userDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userDTO.getUsername(),
                            userDTO.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new ResponseEntity<>(Map.of("user", userDTO.toString()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "Login failed: " + e.getMessage(), "user", userDTO.toString()), HttpStatus.BAD_REQUEST);
        }
    }
}
