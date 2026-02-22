package com.arka.response.save;

import com.arka.enums.OrderStatus;
import com.arka.enums.OrderType;
import com.arka.response.get.CompanyNameResponse;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

public record CreateOrderResponse(
        Long id,
        String number,
        OrderStatus status,
        String notes,
        OrderType type,
        BigDecimal totalPrice,
        Instant createdAt,
        Set<CreateOrderItemResponse> items,
        CompanyNameResponse company
) {
}
