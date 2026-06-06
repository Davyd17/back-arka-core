package com.arka.order.dto;

import com.arka.enums.OrderStatus;
import com.arka.enums.OrderType;
import com.arka.product.dto.ProductSummaryOut;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record UpdateOrderOut(
        Long id,
        String number,
        OrderStatus status,
        String notes,
        OrderType type,
        Instant updatedAt,
        OrderCompany company,
        List<Item> items
) {
    public record Item (
            Long id,
            ProductSummaryOut product,
            int quantity,
            BigDecimal unitPriceSnapshot,
            BigDecimal totalPrice
    ){}

    public record OrderCompany(
            Long id,
            String name
    ){
    }
}
