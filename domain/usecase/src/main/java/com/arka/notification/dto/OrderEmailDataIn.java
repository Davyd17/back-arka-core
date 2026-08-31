package com.arka.notification.dto;

import com.arka.enums.OrderStatus;

public record OrderEmailDataIn(
        String number,
        OrderStatus status,
        String companyName
) {
}
