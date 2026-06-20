package com.smartamal.auth.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "admins")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {

    // PRIMARY KEY
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // NAMA ADMIN
    @Column(name = "full_name", nullable = false)
    private String fullName;

    // EMAIL LOGIN
    @Column(unique = true, nullable = false)
    private String email;

    // PASSWORD LOGIN
    @Column(nullable = false)
    private String password;

    // WAKTU DIBUAT
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // WAKTU UPDATE
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}