package com.arka.request;

import com.arka.exceptions.Required;
import jakarta.validation.Valid;

import java.util.Set;

public record UpdateOrderRequest(

        @Required(field = "notes")
        String notes,
        @Required(field = "items")
        @Valid
        Set<Item> items
) {
        public record Item(
                @Required(field = "productId")
                Long productId,

                @Required(field = "quantity")
                int quantity
        ){}
}
