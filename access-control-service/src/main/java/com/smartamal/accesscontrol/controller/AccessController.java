package com.smartamal.accesscontrol.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartamal.accesscontrol.dto.AccessDTO;
import com.smartamal.accesscontrol.model.AccessLog;
import com.smartamal.accesscontrol.model.RFIDCard;
import com.smartamal.accesscontrol.service.AccessService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/access")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AccessController {

    private final AccessService accessService;

    // =====================================================
    // RFID CARD
    // =====================================================

    @PostMapping("/cards")
    public ResponseEntity<RFIDCard> createCard(
            @RequestBody RFIDCard card) {

        return ResponseEntity.ok(
                accessService.createCard(card));
    }

    @GetMapping("/cards")
    public ResponseEntity<List<RFIDCard>> getAllCards() {

        return ResponseEntity.ok(
                accessService.getAllCards());
    }

    @GetMapping("/cards/{id}")
    public ResponseEntity<RFIDCard> getCardById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                accessService.getCardById(id));
    }

    @PutMapping("/cards/{id}")
    public ResponseEntity<RFIDCard> updateCard(
            @PathVariable Long id,
            @RequestBody RFIDCard card) {

        return ResponseEntity.ok(
                accessService.updateCard(id, card));
    }

    @DeleteMapping("/cards/{id}")
    public ResponseEntity<String> deleteCard(
            @PathVariable Long id) {

        accessService.deleteCard(id);

        return ResponseEntity.ok(
                "RFID Card berhasil dihapus");
    }

    // =====================================================
    // RFID CHECK (POSTMAN TEST)
    // Nanti ESP32 MQTT juga memanggil method yang sama
    // =====================================================

    @PostMapping("/check")
    public ResponseEntity<AccessLog> checkAccess(
            @RequestParam String uid) {

        return ResponseEntity.ok(
                accessService.checkAccess(uid));
    }

    // =====================================================
    // SECURITY SENSOR (POSTMAN TEST)
    // Nanti dipanggil otomatis oleh MQTT
    // =====================================================

    @PostMapping("/security")
    public ResponseEntity<AccessLog> securityWarning() {

        return ResponseEntity.ok(
                accessService.saveSecurityEvent());
    }

        // =====================================================
        // OPEN BOX FROM FLUTTER
        // =====================================================

        @PostMapping("/open")
        public ResponseEntity<AccessDTO> openBox(
                @RequestBody AccessDTO request) {

        return ResponseEntity.ok(
                accessService.openBox(request));
        }

        // =====================================================
        // CLOSE BOX FROM FLUTTER
        // =====================================================

        @PostMapping("/close")
        public ResponseEntity<AccessDTO> closeBox(
                @RequestBody AccessDTO request) {

        return ResponseEntity.ok(
                accessService.closeBox(request));
        }

    // =====================================================
    // ACCESS LOG
    // =====================================================

    @GetMapping("/logs")
    public ResponseEntity<List<AccessLog>> getAllLogs() {

        return ResponseEntity.ok(
                accessService.getAll());
    }

    @GetMapping("/logs/{id}")
    public ResponseEntity<AccessLog> getLogById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                accessService.getById(id));
    }

    @DeleteMapping("/logs/{id}")
    public ResponseEntity<String> deleteLog(
            @PathVariable Long id) {

        accessService.delete(id);

        return ResponseEntity.ok(
                "Access Log berhasil dihapus");
    }

}