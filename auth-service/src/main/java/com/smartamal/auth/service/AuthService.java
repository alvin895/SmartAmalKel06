package com.smartamal.auth.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.smartamal.auth.dto.LoginRequest;
import com.smartamal.auth.dto.RegisterRequest;
import com.smartamal.auth.model.Admin;
import com.smartamal.auth.repository.AdminRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AdminRepository adminRepository;

    private final PasswordEncoder passwordEncoder;

    // REGISTER ADMIN
    public Admin register(RegisterRequest request) {

        if (adminRepository.existsByEmail(request.getEmail())) {

            throw new RuntimeException(
                    "Email sudah terdaftar");
        }

        Admin admin = new Admin();

        admin.setFullName(request.getFullName());

        admin.setEmail(request.getEmail());

        admin.setPassword(
                passwordEncoder.encode(
                        request.getPassword()));

        return adminRepository.save(admin);
    }

    // LOGIN ADMIN
    public Admin login(LoginRequest request) {

        Admin admin = adminRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Email tidak ditemukan"));

        boolean match = passwordEncoder.matches(
                request.getPassword(),
                admin.getPassword());

        if (!match) {

            throw new RuntimeException(
                    "Password salah");
        }

        return admin;
    }

    // GET ALL ADMIN
    public List<Admin> getAllAdmins() {

        return adminRepository.findAll();
    }

    // GET ADMIN BY ID
    public Admin getAdminById(Long id) {

        return adminRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Admin tidak ditemukan"));
    }

    // UPDATE ADMIN
    public Admin updateAdmin(
            Long id,
            RegisterRequest request) {

        Admin admin = adminRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Admin tidak ditemukan"));

        admin.setFullName(request.getFullName());

        admin.setEmail(request.getEmail());

        return adminRepository.save(admin);
    }

    // DELETE ADMIN
    public void deleteAdmin(Long id) {

        Admin admin = adminRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Admin tidak ditemukan"));

        adminRepository.delete(admin);
    }
}