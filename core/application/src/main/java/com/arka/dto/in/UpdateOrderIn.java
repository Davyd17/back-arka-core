package com.arka.dto.in;

import java.util.Set;

public record UpdateOrderIn(

        Long id,
        String notes,
        Set<Item> items
) {

    public record Item(
            Long id,
            Long productId,
            int quantity
    ){
    }
}
