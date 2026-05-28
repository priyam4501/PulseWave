package com.pulsewave.authservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.pulsewave.authservice.entity.User;
import com.pulsewave.authservice.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {

            return username -> {

                    User user = userRepository
                                    .findByEmail(username)
                                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

                    return org.springframework.security.core.userdetails.User
                                    .builder()
                                    .username(user.getEmail())
                                    .password(user.getPassword())
                                    .roles(user.getRole().name().replace("ROLE_", ""))
                                    .build();
            };
    }
  
    @Bean
    public AuthenticationProvider authenticationProvider() {

            DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

            provider.setUserDetailsService(userDetailsService());

            provider.setPasswordEncoder(passwordEncoder());

            return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {

        return config.getAuthenticationManager();
    }
}