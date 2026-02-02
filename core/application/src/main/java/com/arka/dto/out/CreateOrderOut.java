package com.arka.dto.out;

import com.arka.model.enums.OrderStatus;
import com.arka.model.enums.OrderType;

import java.time.Instant;

public record CreateOrderOut (

        String number,
        OrderStatus status,
        String notes,
        OrderType type,
        CompanyCreateOrderOut company,
        Instant createdAt
){

}
