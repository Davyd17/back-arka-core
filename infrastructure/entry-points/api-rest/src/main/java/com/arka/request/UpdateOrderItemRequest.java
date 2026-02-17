package com.arka.request;

import com.arka.exceptions.Required;

public record UpdateOrderItemRequest(

        @Required(field = "productId")
        Long productId,

        @Required(field = "quantity")
        int quantity
) {
}
