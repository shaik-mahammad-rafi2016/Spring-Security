package com.security.Jwt.Implementation.Project.controller;


import com.security.Jwt.Implementation.Project.dto.AuthResponse;
import com.security.Jwt.Implementation.Project.dto.LoginRequest;
import com.security.Jwt.Implementation.Project.dto.RegisterRequest;
import com.security.Jwt.Implementation.Project.entity.User;
import com.security.Jwt.Implementation.Project.security.service.JwtService;
import com.security.Jwt.Implementation.Project.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;



import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(UserService userService,AuthenticationManager authenticationManager,JwtService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid @RequestBody RegisterRequest request) {

        User user = userService.registerUser(request);

        return ResponseEntity.ok(
                "User registered successfully: " + user.getUsername()
        );

    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(),
                                request.getPassword()
                        )
                );

        String token = jwtService.generateToken(authentication);

        AuthResponse response =
                new AuthResponse(token, "Bearer", 3600);

        return ResponseEntity.ok(response);
    }
}
