package com.arka.order.dto;

import com.arka.enums.OrderStatus;
import com.arka.enums.OrderType;
import com.arka.product.dto.ProductSummaryOut;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

public record CreateOrderOut (

        Long id,
        String number,
        OrderStatus status,
        String notes,
        OrderType type,
        BigDecimal totalPrice,
        OrderCompany company,
        Set<Item> items,
        Instant createdAt
){
    public record Item(
            Long id,
            ProductSummaryOut product,
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
