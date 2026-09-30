package com.arka.notification.dto;

import com.arka.enums.OrderStatus;

public record OrderStatusEmailCommand(
        String recipient,
        String orderNumber,
        OrderStatus status,
        String companyName
) {
}
