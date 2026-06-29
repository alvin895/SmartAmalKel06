package com.smartamal.accesscontrol.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessDTO {

    // =====================================================
    // REQUEST DARI FLUTTER
    // =====================================================

    // ID Device / Kotak Amal
    private String deviceId;

    // OPEN / CLOSE
    private String command;

    // =====================================================
    // RESPONSE KE FLUTTER
    // =====================================================

    // SUCCESS / FAILED
    private String status;

    // Pesan yang ditampilkan di Flutter
    private String message;

}