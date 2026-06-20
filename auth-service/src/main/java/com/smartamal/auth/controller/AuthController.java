package com.smartamal.auth.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartamal.auth.dto.AuthResponse;
import com.smartamal.auth.dto.LoginRequest;
import com.smartamal.auth.dto.RegisterRequest;
import com.smartamal.auth.model.Admin;
import com.smartamal.auth.security.JwtUtil;
import com.smartamal.auth.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final JwtUtil jwtUtil;

    // REGISTER
    @PostMapping("/register")
    public ResponseEntity<Admin> register(
            @Valid @RequestBody RegisterRequest request) {

        Admin admin =
                authService.register(request);

        return ResponseEntity.ok(admin);
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {

        Admin admin =
                authService.login(request);

        String token =
                jwtUtil.generateToken(admin);

        AuthResponse response =
                new AuthResponse(
                        token,
                        "Bearer",
                        admin.getEmail());

        return ResponseEntity.ok(response);
    }

    // GET ALL ADMIN
    @GetMapping("/admins")
    public ResponseEntity<List<Admin>> getAllAdmins() {

        return ResponseEntity.ok(
                authService.getAllAdmins());
    }

    // GET ADMIN BY ID
    @GetMapping("/admins/{id}")
    public ResponseEntity<Admin> getAdminById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                authService.getAdminById(id));
    }

    // UPDATE ADMIN
    @PutMapping("/admins/{id}")
    public ResponseEntity<Admin> updateAdmin(
            @PathVariable Long id,
            @RequestBody RegisterRequest request) {

        return ResponseEntity.ok(
                authService.updateAdmin(
                        id,
                        request));
    }

    // DELETE ADMIN
    @DeleteMapping("/admins/{id}")
    public ResponseEntity<String> deleteAdmin(
            @PathVariable Long id) {

        authService.deleteAdmin(id);

        return ResponseEntity.ok(
                "Admin berhasil dihapus");
    }
}