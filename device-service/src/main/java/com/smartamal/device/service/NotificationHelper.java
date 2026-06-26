package com.smartamal.device.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.smartamal.device.dto.NotificationRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationHelper {

    private final RestTemplate restTemplate;

    @Value("${notification.service.url}")
    private String notificationServiceUrl;

    @Value("${notification.default.email}")
    private String defaultEmail;

    // =====================================================
    // DONATION NOTIFICATION
    // =====================================================
    public void sendDonationNotification(
            Integer nominal,
            String deviceName) {

        NotificationRequest request =
                NotificationRequest.builder()
                        .title("Donasi Baru")
                        .message(
                                "Donasi sebesar Rp "
                                        + nominal
                                        + " berhasil diterima dari "
                                        + deviceName)
                        .type("DONATION")
                        .recipientEmail(defaultEmail)
                        .build();

        send(request);
    }

    // =====================================================
    // SECURITY NOTIFICATION
    // =====================================================
    public void sendSecurityNotification(
            String message) {

        NotificationRequest request =
                NotificationRequest.builder()
                        .title("Peringatan Keamanan")
                        .message(message)
                        .type("SECURITY")
                        .recipientEmail(defaultEmail)
                        .build();

        send(request);
    }

    // =====================================================
    // DEVICE STATUS NOTIFICATION
    // =====================================================
    public void sendDeviceStatusNotification(
            String deviceName,
            String status) {

        NotificationRequest request =
                NotificationRequest.builder()
                        .title("Status Device")
                        .message(
                                "Device "
                                        + deviceName
                                        + " berubah menjadi "
                                        + status)
                        .type("DEVICE")
                        .recipientEmail(defaultEmail)
                        .build();

        send(request);
    }

    // =====================================================
    // SEND TO NOTIFICATION SERVICE
    // =====================================================
    private void send(
            NotificationRequest request) {

        try {

            HttpHeaders headers =
                    new HttpHeaders();

            headers.setContentType(
                    MediaType.APPLICATION_JSON);

            HttpEntity<NotificationRequest> entity =
                    new HttpEntity<>(
                            request,
                            headers);

            restTemplate.postForEntity(
                    notificationServiceUrl,
                    entity,
                    String.class);

            log.info(
                    "Notification [{}] berhasil dikirim.",
                    request.getType());

        } catch (Exception e) {

            log.error(
                    "Gagal menghubungi Notification Service : {}",
                    e.getMessage());

        }
    }
}