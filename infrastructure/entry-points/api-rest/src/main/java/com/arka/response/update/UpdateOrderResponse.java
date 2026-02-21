package com.arka.response.update;

import com.arka.enums.OrderStatus;
import com.arka.enums.OrderType;
import com.arka.model.order.OrderItem;
import com.arka.response.get.CompanyNameResponse;

import java.time.Instant;
import java.util.List;

public record UpdateOrderResponse(
        Long id,
        String number,
        OrderStatus status,
        String notes,
        OrderType type,
        Instant updatedAt,
        CompanyNameResponse company,
        List<OrderItem> items
) {
}
