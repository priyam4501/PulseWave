package com.pulsewave.authservice.service;

import com.pulsewave.authservice.dto.*;
import com.pulsewave.authservice.entity.User;
import com.pulsewave.authservice.repository.UserRepository;
import com.pulsewave.authservice.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public String register(RegisterRequest request) {

        log.info("Register request received for email: {}", request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {

            log.warn("Email already exists: {}", request.getEmail());

            throw new RuntimeException("Email already registered");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("USER")
                .build();

        userRepository.save(user);

        log.info("User registered successfully");

        return "User registered successfully";
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String token =
                jwtService.generateToken(request.getEmail());

        log.info("User logged in successfully: {}", request.getEmail());

        return AuthResponse.builder()
                .token(token)
                .build();
    }
}