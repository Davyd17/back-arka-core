package com.arka.order.dto;

import com.arka.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.Instant;

public record OrderOut(
        Long id,
        String number,
        OrderStatus status,
        BigDecimal totalPrice,
        String notes,
        Instant createdAt,
        Instant updatedAt
) {

}
