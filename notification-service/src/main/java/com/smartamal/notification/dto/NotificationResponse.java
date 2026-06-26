package com.smartamal.notification.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {

    private Long id;

    private String title;

    private String message;

    private String type;

    private String status;

    private String recipientEmail;

    private LocalDateTime createdAt;

}