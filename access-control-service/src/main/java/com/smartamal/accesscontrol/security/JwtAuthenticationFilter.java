package com.smartamal.accesscontrol.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("======================================");
        System.out.println("ACCESS CONTROL JWT FILTER");
        System.out.println("METHOD : " + request.getMethod());
        System.out.println("URI    : " + request.getRequestURI());

        String authHeader = request.getHeader("Authorization");

        System.out.println("AUTH HEADER : " + authHeader);

        String token = null;
        String email = null;

        try {

            if (authHeader != null &&
                    authHeader.startsWith("Bearer ")) {

                token = authHeader.substring(7);

                System.out.println("TOKEN : " + token);

                email = jwtService.extractEmail(token);

                System.out.println("EMAIL : " + email);

            }

            if (email != null &&
                    SecurityContextHolder.getContext()
                            .getAuthentication() == null) {

                if (jwtService.validateToken(token)) {

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(

                                    email,

                                    null,

                                    Collections.singletonList(
                                            new SimpleGrantedAuthority(
                                                    "ROLE_ADMIN")));

                    authToken.setDetails(

                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request));

                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authToken);

                    System.out.println("JWT VALID");
                } else {

                    System.out.println("JWT TIDAK VALID");
                }

            }

        } catch (JwtException e) {

            System.out.println("JWT ERROR");
            e.printStackTrace();

        } catch (Exception e) {

            System.out.println("FILTER ERROR");
            e.printStackTrace();

        }

        filterChain.doFilter(request, response);

    }

}