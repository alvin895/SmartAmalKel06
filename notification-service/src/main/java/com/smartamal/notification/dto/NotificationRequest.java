package com.smartamal.notification.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NotificationRequest {

    @NotBlank(message = "Title tidak boleh kosong")
    private String title;

    @NotBlank(message = "Message tidak boleh kosong")
    private String message;

    @NotBlank(message = "Type tidak boleh kosong")
    private String type;

    @Email(message = "Format email tidak valid")
    private String recipientEmail;
}
