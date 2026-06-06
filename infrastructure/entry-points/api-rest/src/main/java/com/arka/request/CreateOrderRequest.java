package com.arka.request;

import com.arka.exceptions.Required;
import com.arka.enums.OrderType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record CreateOrderRequest(

        @Required(field = "number")
        String number,

        String notes,

        @NotNull(message = "Order type is required")
        OrderType type,

        @NotEmpty(message = "There must be at least one item")
        Set<Item> items,

        @Required(field = "company id")
        Long companyId
) {

        public record Item(
                @Required(field = "product id")
                Long productId,

                @Min(value = 1, message = "Quantity must be at least 1")
                int quantity
        ){}
}
