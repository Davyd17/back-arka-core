package com.arka.response;

import com.arka.model.enums.OrderStatus;
import com.arka.model.enums.OrderType;
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
