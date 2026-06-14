package com.arka.notification.dto;

public record EmailMessage(
        String sender,
        String recipient,
        String subject,
        String body
) {
}
