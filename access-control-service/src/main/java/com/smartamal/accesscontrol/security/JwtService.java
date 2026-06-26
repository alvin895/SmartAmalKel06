package com.smartamal.accesscontrol.security;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey getSigningKey() {

        return Keys.hmacShaKeyFor(
                secret.getBytes(
                        StandardCharsets.UTF_8));
    }

    public String extractEmail(
            String token) {

        return extractClaims(token)
                .getSubject();
    }

    public boolean validateToken(
            String token) {

        try {

            extractClaims(token);

            return true;

        } catch (Exception e) {

            return false;
        }
    }

    private Claims extractClaims(
            String token) {

        return Jwts.parser()
                .verifyWith(
                        getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}