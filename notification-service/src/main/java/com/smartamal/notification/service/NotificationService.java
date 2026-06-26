package com.smartamal.notification.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.smartamal.notification.dto.NotificationRequest;
import com.smartamal.notification.dto.NotificationResponse;
import com.smartamal.notification.model.Notification;
import com.smartamal.notification.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;

    private final JavaMailSender mailSender;

    // =====================================================
    // CREATE NOTIFICATION
    // =====================================================

    public NotificationResponse createNotification(NotificationRequest request) {

        Notification notification = Notification.builder()
                .title(request.getTitle())
                .message(request.getMessage())
                .type(request.getType())
                .status("PENDING")
                .recipientEmail(request.getRecipientEmail())
                .build();

        Notification saved = notificationRepository.save(notification);

        if (request.getRecipientEmail() != null &&
                !request.getRecipientEmail().isBlank()) {

            try {

                sendEmail(
                        request.getRecipientEmail(),
                        request.getTitle(),
                        request.getMessage());

                saved.setStatus("SENT");

                notificationRepository.save(saved);

                log.info("Email berhasil dikirim ke {}",
                        request.getRecipientEmail());

            } catch (Exception e) {

                e.printStackTrace();

                log.error(
                        "Gagal mengirim email : {}",
                        e.getMessage());

                saved.setStatus("FAILED");

                notificationRepository.save(saved);
            }

        }

        return mapToResponse(saved);
    }

    // =====================================================
    // GET ALL
    // =====================================================

    public List<NotificationResponse> getAllNotifications() {

        return notificationRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

    }

    // =====================================================
    // GET BY ID
    // =====================================================

    public NotificationResponse getNotificationById(Long id) {

        Notification notification =
                notificationRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Notification tidak ditemukan"));

        return mapToResponse(notification);

    }

    // =====================================================
    // UPDATE
    // =====================================================

    public NotificationResponse updateNotification(
            Long id,
            NotificationRequest request) {

        Notification notification =
                notificationRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Notification tidak ditemukan"));

        notification.setTitle(request.getTitle());

        notification.setMessage(request.getMessage());

        notification.setType(request.getType());

        notification.setRecipientEmail(
                request.getRecipientEmail());

        Notification updated =
                notificationRepository.save(notification);

        return mapToResponse(updated);

    }

    // =====================================================
    // DELETE
    // =====================================================

    public void deleteNotification(Long id) {

        Notification notification =
                notificationRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Notification tidak ditemukan"));

        notificationRepository.delete(notification);

    }

    // =====================================================
    // SEND EMAIL
    // =====================================================

    private void sendEmail(
            String to,
            String subject,
            String message) {

        SimpleMailMessage mail =
                new SimpleMailMessage();

        mail.setTo(to);

        mail.setSubject(subject);

        mail.setText(message);

        mailSender.send(mail);

    }

    // =====================================================
    // ENTITY -> RESPONSE
    // =====================================================

    private NotificationResponse mapToResponse(
            Notification notification) {

        return NotificationResponse.builder()

                .id(notification.getId())

                .title(notification.getTitle())

                .message(notification.getMessage())

                .type(notification.getType())

                .status(notification.getStatus())

                .recipientEmail(
                        notification.getRecipientEmail())

                .createdAt(
                        notification.getCreatedAt())

                .build();

    }

}