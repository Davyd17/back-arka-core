package com.arka.dto.out;

import com.arka.enums.OrderStatus;
import com.arka.enums.OrderType;

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
        CompanySummaryOut company,
        Set<CreateOrderItemOut> items,
        Instant createdAt
){

}
