package com.smartamal.accesscontrol.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rfid_cards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RFIDCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // UID RFID
    @Column(name = "uid", nullable = false, unique = true)
    private String uid;

    // Nama Pemilik Kartu
    @Column(name = "owner_name", nullable = false)
    private String ownerName;

    // Status kartu
    @Column(nullable = false)
    private Boolean active;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {

        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }

        if (active == null) {
            active = true;
        }
    }

}