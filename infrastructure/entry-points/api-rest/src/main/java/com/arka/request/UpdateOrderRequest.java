package com.arka.request;

import com.arka.exceptions.Required;

import java.util.Set;

public record UpdateOrderRequest(

        @Required(field = "id")
        Long id,
        @Required(field = "notes")
        String notes,
        @Required(field = "items")
        Set<UpdateOrderItemRequest> items
) {
}
