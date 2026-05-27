package com.pulsewave.authservice.service;

import com.pulsewave.authservice.dto.AuthResponse;
import com.pulsewave.authservice.dto.LoginRequest;
import com.pulsewave.authservice.dto.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}