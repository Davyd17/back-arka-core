package com.arka.notification.dto;

import com.arka.enums.OrderStatus;

import java.time.Instant;

public record OrderStatusChangeRequestedEvent(
        String orderNumber,
        OrderStatus status,
        String companyName,
        String recipient,
        Instant requestedAt
) {
}
