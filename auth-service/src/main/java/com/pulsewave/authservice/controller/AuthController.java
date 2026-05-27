package com.pulsewave.authservice.controller;

import com.pulsewave.authservice.dto.AuthResponse;
import com.pulsewave.authservice.dto.LoginRequest;
import com.pulsewave.authservice.dto.RegisterRequest;
import com.pulsewave.authservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
}