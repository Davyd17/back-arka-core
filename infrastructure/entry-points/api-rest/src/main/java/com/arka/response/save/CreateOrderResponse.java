package com.arka.response.save;

import com.arka.enums.OrderStatus;
import com.arka.enums.OrderType;
import com.arka.response.get.ProductSummaryResponse;

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
        Set<Item> items,
        OrderCompany company
) {
    public record Item(
            Long id,
            ProductSummaryResponse product,
            int quantity,
            BigDecimal unitPriceSnapshot,
            BigDecimal totalPrice
    ){}

    public record OrderCompany(
            Long id,
            String name
    ){}
}
