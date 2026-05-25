package com.pulsewave.authservice.service;

import com.pulsewave.authservice.dto.*;

public interface AuthService {

    String register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}