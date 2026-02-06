package com.arka.dto.in;

import com.arka.model.enums.OrderStatus;
import com.arka.model.enums.OrderType;

import java.util.Set;

public record CreateOrderIn (

        String number,
        OrderStatus status,
        String notes,
        OrderType type,
        Long companyId,
        Set<CreateOrderItemIn> items
){

}
