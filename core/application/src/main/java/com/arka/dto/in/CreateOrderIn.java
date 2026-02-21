package com.arka.dto.in;

import com.arka.enums.OrderType;

import java.util.List;

public record CreateOrderIn (

        String number,
        String notes,
        OrderType type,
        Long companyId,
        List<CreateOrderItemIn> items
){

}
