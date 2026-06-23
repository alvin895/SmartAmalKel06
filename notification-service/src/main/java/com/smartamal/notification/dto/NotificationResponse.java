package com.smartamal.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationResponse {

    private Long id;

    private String title;

    private String message;

    private String type;

    private String status;
}