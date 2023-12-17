package com.example.youdown.services.userservice.impl;

import com.example.youdown.exceptions.UserRegistrationException;
import com.example.youdown.models.UserEntity;
import com.example.youdown.repository.UserRepository;
import com.example.youdown.services.userservice.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(UserEntity registerUser) {
        try {
            userRepository.save(registerUser);
            log.info(registerUser.getUsername() + " registered successfully");
        } catch (DataAccessException exception) {
            throw new UserRegistrationException("Could not register user: " + exception.getMessage());
        }
    }

    @Override
    public Optional<UserEntity> findUser(UserEntity loginUser) {
        Optional<UserEntity> foundUserOptional = userRepository.findByUsername(loginUser.getUsername());

        if (foundUserOptional.isPresent()) {
            UserEntity foundUser = foundUserOptional.get();

            String storedPassword = foundUser.getPassword();
            String loginPassword = loginUser.getPassword();

            if (storedPassword.equals(loginPassword)) {
                return foundUserOptional;
            }
        }
        return Optional.empty();
    }
}
