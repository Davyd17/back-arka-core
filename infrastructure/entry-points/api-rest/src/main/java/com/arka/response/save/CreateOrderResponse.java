package com.arka.response.save;

import com.arka.model.enums.OrderStatus;
import com.arka.model.enums.OrderType;
import com.arka.response.get.CompanyCreateOrderResponse;

import java.time.Instant;
import java.util.Set;

public record CreateOrderResponse(
        Long id,
        String number,
        OrderStatus status,
        String notes,
        OrderType type,
        Instant createdAt,
        Set<CreateOrderItemResponse> items,
        CompanyCreateOrderResponse company
) {
}
