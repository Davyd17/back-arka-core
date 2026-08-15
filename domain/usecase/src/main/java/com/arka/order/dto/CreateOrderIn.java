package com.arka.order.dto;

import com.arka.enums.OrderType;

import java.util.List;

public record CreateOrderIn (
        String notes,
        OrderType type,
        List<Item> items
){

    public record Item(
            Long productId,
            int quantity
    ) {
    }

}
