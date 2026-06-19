package com.arka.notification.dto;

import lombok.Builder;

public record EmailMessage(
        String sender,
        String recipient,
        String subject,
        String body
) {
    @Builder(toBuilder = true)
    public EmailMessage {
    }
}
