package com.pulsewave.authservice.service;

import com.pulsewave.authservice.dto.AuthResponse;
import com.pulsewave.authservice.dto.LoginRequest;
import com.pulsewave.authservice.dto.RegisterRequest;
import com.pulsewave.authservice.entity.Role;
import com.pulsewave.authservice.entity.User;
import com.pulsewave.authservice.repository.UserRepository;
import com.pulsewave.authservice.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl
        implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    @Override
    public AuthResponse register(
            RegisterRequest request
    ) {

        if (
                userRepository.existsByEmail(
                        request.getEmail()
                )
        ) {

            throw new RuntimeException(
                    "Email already exists"
            );
        }

        if (
                userRepository.existsByUsername(
                        request.getUsername()
                )
        ) {

            throw new RuntimeException(
                    "Username already exists"
            );
        }

        User user = User.builder()

                .username(
                        request.getUsername()
                )

                .email(
                        request.getEmail()
                )

                .password(
                        passwordEncoder.encode(
                                request.getPassword()
                        )
                )

                .role(Role.USER)

                .build();

        User savedUser =
                userRepository.save(user);

        log.info(
                "User registered successfully: {}",
                savedUser.getUsername()
        );

        String token =
                jwtService.generateToken(

                        savedUser.getId(),

                        savedUser.getUsername(),

                        savedUser.getEmail()
                );

        return AuthResponse.builder()

                .token(token)

                .username(
                        savedUser.getUsername()
                )

                .email(
                        savedUser.getEmail()
                )

                .build();
    }

    @Override
    public AuthResponse login(
            LoginRequest request
    ) {

        User user = userRepository

                .findByEmail(
                        request.getEmail()
                )

                .orElseThrow(() ->

                        new RuntimeException(
                                "Invalid credentials"
                        )
                );

        if (
                !passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword()
                )
        ) {

            throw new RuntimeException(
                    "Invalid credentials"
            );
        }

        log.info(
                "User logged in successfully: {}",
                user.getUsername()
        );

        String token =
                jwtService.generateToken(

                        user.getId(),

                        user.getUsername(),

                        user.getEmail()
                );

        return AuthResponse.builder()

                .token(token)

                .username(
                        user.getUsername()
                )

                .email(
                        user.getEmail()
                )

                .build();
    }
}