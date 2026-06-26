package com.smartamal.notification.controller;

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

import com.smartamal.notification.dto.NotificationRequest;
import com.smartamal.notification.dto.NotificationResponse;
import com.smartamal.notification.service.NotificationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    // ==========================
    // CREATE NOTIFICATION
    // ==========================
    @PostMapping
    public ResponseEntity<NotificationResponse> createNotification(
            @Valid @RequestBody NotificationRequest request) {

        return ResponseEntity.ok(
                notificationService.createNotification(request));
    }

    // ==========================
    // GET ALL NOTIFICATIONS
    // ==========================
    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getAllNotifications() {

        return ResponseEntity.ok(
                notificationService.getAllNotifications());
    }

    // ==========================
    // GET NOTIFICATION BY ID
    // ==========================
    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponse> getNotificationById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                notificationService.getNotificationById(id));
    }

    // ==========================
    // UPDATE NOTIFICATION
    // ==========================
    @PutMapping("/{id}")
    public ResponseEntity<NotificationResponse> updateNotification(
            @PathVariable Long id,
            @Valid @RequestBody NotificationRequest request) {

        return ResponseEntity.ok(
                notificationService.updateNotification(id, request));
    }

    // ==========================
    // DELETE NOTIFICATION
    // ==========================
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNotification(
            @PathVariable Long id) {

        notificationService.deleteNotification(id);

        return ResponseEntity.ok(
                "Notification berhasil dihapus");
    }

}