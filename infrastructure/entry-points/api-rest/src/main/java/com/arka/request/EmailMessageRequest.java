package com.arka.request;

public record EmailMessageRequest(
        String sender,
        String recipient,
        String subject,
        String body
) {
}
