package com.smartamal.accesscontrol.security;

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

                // =====================================
                // Disable CSRF
                // =====================================
                .csrf(csrf -> csrf.disable())

                // =====================================
                // Enable CORS
                // =====================================
                .cors(Customizer.withDefaults())

                // =====================================
                // Stateless Session
                // =====================================
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS))

                // =====================================
                // Authorization
                // =====================================
                .authorizeHttpRequests(auth -> auth

                        // Preflight Request
                        .requestMatchers(HttpMethod.OPTIONS, "/**")
                        .permitAll()

                        // Monitoring
                        .requestMatchers("/actuator/**")
                        .permitAll()

                        // Semua endpoint Access Control wajib JWT
                        .anyRequest()
                        .authenticated())

                // =====================================
                // JWT Filter
                // =====================================
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class)

                // =====================================
                // Authentication Error
                // =====================================
                .exceptionHandling(exception ->
                        exception.authenticationEntryPoint(
                                new HttpStatusEntryPoint(UNAUTHORIZED)));

        return http.build();
    }
}