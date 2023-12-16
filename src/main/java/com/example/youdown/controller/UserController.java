package com.example.youdown.controller;

import com.example.youdown.dto.UserDTO;
import com.example.youdown.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerToSystem(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginToSystem(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
