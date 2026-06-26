package com.smartamal.accesscontrol.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.smartamal.accesscontrol.dto.NotificationRequest;
import com.smartamal.accesscontrol.model.AccessLog;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationHelper {

    private final RestTemplate restTemplate;

    @Value("${notification.service.url}")
    private String notificationServiceUrl;

    @Value("${notification.default.email}")
    private String defaultRecipient;

    public void sendNotification(AccessLog accessLog) {

        try {

            NotificationRequest request = NotificationRequest.builder()
                    .title(createTitle(accessLog))
                    .message(createMessage(accessLog))
                    .type(accessLog.getEventType())
                    .recipientEmail(defaultRecipient)
                    .build();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<NotificationRequest> entity =
                    new HttpEntity<>(request, headers);

            restTemplate.postForEntity(
                    notificationServiceUrl,
                    entity,
                    Void.class);

            log.info("Notification berhasil dikirim ke Notification Service.");

        } catch (Exception e) {

            log.error("Gagal mengirim notification : {}", e.getMessage());

        }

    }

    // =====================================================
    // TITLE
    // =====================================================

    private String createTitle(AccessLog log) {

        switch (log.getEventType().toUpperCase()) {

            case "SECURITY":
                return "Peringatan Keamanan";

            case "ACCESS":
                return "Akses RFID";

            default:
                return "Smart Amal Notification";
        }
    }

    // =====================================================
    // MESSAGE
    // =====================================================

    private String createMessage(AccessLog log) {

        switch (log.getEventType().toUpperCase()) {

            case "SECURITY":
                return "Sensor getar mendeteksi aktivitas mencurigakan.";

            case "ACCESS":
                return "RFID "
                        + log.getOwnerName()
                        + " : "
                        + log.getStatus();

            default:
                return log.getStatus();
        }
    }

}