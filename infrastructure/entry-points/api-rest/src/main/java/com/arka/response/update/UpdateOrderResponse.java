package com.arka.response.update;

import com.arka.enums.OrderStatus;
import com.arka.enums.OrderType;
import com.arka.response.get.ProductSummaryResponse;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record UpdateOrderResponse(
        Long id,
        String number,
        OrderStatus status,
        String notes,
        OrderType type,
        Instant updatedAt,
        OrderCompany company,
        List<Item> items
) {
    public record Item(
            Long id,
            ProductSummaryResponse product,
            int quantity,
            BigDecimal unitPriceSnapshot,
            BigDecimal totalPrice
    ){
    }

    public record OrderCompany(
            Long id,
            String name
    ){
    }
}
