package com.smartamal.auth.security;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.smartamal.auth.model.Admin;
import com.smartamal.auth.repository.AdminRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomAdminDetailsService
        implements UserDetailsService {

    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Admin admin = adminRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Admin tidak ditemukan"));

        return User.builder()
                .username(admin.getEmail())
                .password(admin.getPassword())
                .authorities(
                        Collections.singletonList(
                                new SimpleGrantedAuthority(
                                        "ROLE_ADMIN")))
                .build();
    }
}