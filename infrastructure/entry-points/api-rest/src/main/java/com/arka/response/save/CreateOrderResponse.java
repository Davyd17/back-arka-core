package com.arka.response.save;

import com.arka.model.enums.OrderStatus;
import com.arka.model.enums.OrderType;
import com.arka.response.get.CompanyCreateOrderResponse;

import java.time.Instant;

public record CreateOrderResponse(
        Long id,
        String number,
        OrderStatus status,
        String notes,
        OrderType type,
        Instant createdAt,
        CompanyCreateOrderResponse company
) {
}
