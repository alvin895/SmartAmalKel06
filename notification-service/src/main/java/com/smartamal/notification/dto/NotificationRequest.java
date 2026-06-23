package com.smartamal.notification.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NotificationRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String message;

    @NotBlank
    private String type;

    private String recipientEmail;
}