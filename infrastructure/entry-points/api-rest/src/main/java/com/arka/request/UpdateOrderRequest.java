package com.arka.request;

import com.arka.exceptions.Required;

import java.util.Set;

public record UpdateOrderRequest(

        @Required(field = "id")
        Long id,
        @Required(field = "notes")
        String notes,
        @Required(field = "items")
        Set<Item> items
) {
        public record Item(
                @Required(field = "productId")
                Long productId,

                @Required(field = "quantity")
                int quantity
        ){}
}
