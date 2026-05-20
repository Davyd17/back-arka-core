package com.arka.order.dto;

import com.arka.entities.Company;
import com.arka.entities.order.OrderItem;
import com.arka.enums.OrderStatus;
import com.arka.enums.OrderType;

import java.time.Instant;
import java.util.List;

public record UpdateOrderOut(
        Long id,
        String number,
        OrderStatus status,
        String notes,
        OrderType type,
        Instant updatedAt,
        Company company,
        List<OrderItem> items
) {
}
