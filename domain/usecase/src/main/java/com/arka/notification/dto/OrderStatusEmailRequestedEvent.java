package com.arka.notification.dto;

import com.arka.enums.OrderStatus;

public record OrderStatusEmailRequestedEvent(
        String orderNumber,
        OrderStatus status,
        String companyName,
        String recipient
) {
}
