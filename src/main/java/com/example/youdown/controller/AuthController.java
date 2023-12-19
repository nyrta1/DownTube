package com.example.youdown.controller;

import com.example.youdown.mapper.UserMapper;
import com.example.youdown.models.UserEntity;
import com.example.youdown.payload.request.LoginRequest;
import com.example.youdown.payload.response.JwtResponse;
import com.example.youdown.security.custom.CustomUserDetailsImpl;
import com.example.youdown.security.jwt.JwtUtils;
import com.example.youdown.services.userservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerToSystem(@RequestBody LoginRequest loginRequest) {
        try {
            UserEntity registerUser = UserMapper.mapToUser(loginRequest);
            userService.saveUser(registerUser);
            return new ResponseEntity<>(Map.of("message", "User registered successfully"), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("message", "Registration failed: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginToSystem(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwtToken = jwtUtils.generateJwtToken(authentication);

            CustomUserDetailsImpl userDetails = (CustomUserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new JwtResponse(jwtToken,
                                    userDetails.getId(),
                                    userDetails.getUsername(),
                                    roles));
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PostMapping("/validate-jwt-token")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> validateJwtToken(@RequestBody JwtResponse jwtResponse) {
        if (jwtUtils.validateJwtToken(jwtResponse.getToken())) {
            return ResponseEntity.ok("Token is not expired");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired JWT token");
        }
    }
}