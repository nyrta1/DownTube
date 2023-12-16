package com.example.youdown.services.impl;

import com.example.youdown.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Override
    public void login() {
        log.info("USER IS TRYING TO LOGIN");
    }
}
