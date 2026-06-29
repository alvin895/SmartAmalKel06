package com.smartamal.apigateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http)
            throws Exception {

        http

                // ===========================
                // DISABLE CSRF
                // ===========================
                .csrf(csrf -> csrf.disable())

                // ===========================
                // ENABLE CORS
                // ===========================
                .cors(Customizer.withDefaults())

                // ===========================
                // STATELESS
                // ===========================
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS))

                // ===========================
                // AUTHORIZATION
                // ===========================
                .authorizeHttpRequests(auth -> auth

                        // Preflight Request
                        .requestMatchers(HttpMethod.OPTIONS, "/**")
                        .permitAll()

                        // Login & Register
                        .requestMatchers("/api/auth/**")
                        .permitAll()

                        // Actuator
                        .requestMatchers("/actuator/**")
                        .permitAll()

                        // Semua endpoint lain wajib JWT
                        .anyRequest()
                        .authenticated())

                // ===========================
                // JWT FILTER
                // ===========================
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class)

                // ===========================
                // AUTH ERROR
                // ===========================
                .exceptionHandling(exception ->
                        exception.authenticationEntryPoint(
                                new HttpStatusEntryPoint(UNAUTHORIZED)));

        return http.build();
    }
}