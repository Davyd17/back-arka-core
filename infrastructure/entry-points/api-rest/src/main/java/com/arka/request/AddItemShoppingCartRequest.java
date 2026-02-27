package com.arka.request;

import com.arka.exceptions.Required;
import jakarta.validation.constraints.Min;

public record AddItemShoppingCartRequest(

        @Required(field = "product id")
        Long productId,

        @Required(field = "quantity")
        @Min(value = 1, message = "Quantity must be at least 1")
        int quantity
) {
}
