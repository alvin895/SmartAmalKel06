package com.smartamal.notification.service;

import java.util.List;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.smartamal.notification.dto.NotificationRequest;
import com.smartamal.notification.model.Notification;
import com.smartamal.notification.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    private final JavaMailSender mailSender;

    // CREATE NOTIFICATION
    public Notification createNotification(
            NotificationRequest request) {

        Notification notification =
                new Notification();

        notification.setTitle(
                request.getTitle());

        notification.setMessage(
                request.getMessage());

        notification.setType(
                request.getType());

        notification.setRecipientEmail(
                request.getRecipientEmail());

        notification.setStatus(
                "SENT");

        Notification savedNotification =
                notificationRepository.save(
                        notification);

        // KIRIM EMAIL JIKA ADA EMAIL TUJUAN
        if (request.getRecipientEmail() != null
                && !request.getRecipientEmail().isEmpty()) {

            sendEmail(
                    request.getRecipientEmail(),
                    request.getTitle(),
                    request.getMessage());
        }

        return savedNotification;
    }

    // SEND EMAIL
    public void sendEmail(
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

    // GET ALL
    public List<Notification> getAllNotifications() {

        return notificationRepository.findAll();
    }

    // GET BY ID
    public Notification getNotificationById(
            Long id) {

        return notificationRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Notification tidak ditemukan"));
    }

    // UPDATE
    public Notification updateNotification(
            Long id,
            NotificationRequest request) {

        Notification notification =
                getNotificationById(id);

        notification.setTitle(
                request.getTitle());

        notification.setMessage(
                request.getMessage());

        notification.setType(
                request.getType());

        notification.setRecipientEmail(
                request.getRecipientEmail());

        return notificationRepository.save(
                notification);
    }

    // DELETE
    public void deleteNotification(
            Long id) {

        Notification notification =
                getNotificationById(id);

        notificationRepository.delete(
                notification);
    }
}