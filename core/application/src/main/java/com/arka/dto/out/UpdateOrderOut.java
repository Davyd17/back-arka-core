package com.arka.dto.out;

import com.arka.model.Company;
import com.arka.enums.OrderStatus;
import com.arka.enums.OrderType;
import com.arka.model.order.OrderItem;

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
