package com.arka.request;

import com.arka.exceptions.Required;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;

import java.math.BigDecimal;

public record CreateOrderItemRequest(

        @Required(field = "product id")
        Long productId,

        @Required(field = "quantity")
        int quantity
) {
}
