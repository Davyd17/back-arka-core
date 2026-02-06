package com.arka.dto.out;

import com.arka.model.enums.OrderStatus;
import com.arka.model.enums.OrderType;

import java.time.Instant;
import java.util.Set;

public record CreateOrderOut (

        Long id,
        String number,
        OrderStatus status,
        String notes,
        OrderType type,
        CompanyCreateOrderOut company,
        Set<CreateOrderItemOut> items,
        Instant createdAt
){

}
