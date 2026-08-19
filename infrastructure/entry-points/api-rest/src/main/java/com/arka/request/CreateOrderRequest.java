package com.arka.request;

import com.arka.exceptions.Required;
import com.arka.enums.OrderType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record CreateOrderRequest(

        @Schema(description = "Optional notes for order", example = "Specific case")
        String notes,

        @NotNull(message = "Order type is required")
        @Schema(description = "Orders can be  created for customers or to suppliers", example = "SALES")
        OrderType type,

        @NotEmpty(message = "There must be at least one item")
        @Valid
        Set<Item> items
) {

        @Schema(name = "CreateOrderItemRequest")
        public record Item(
                @Required(field = "product id")
                @Schema(example = "3")
                Long productId,

                @Min(value = 1, message = "Quantity must be at least 1")
                int quantity
        ){}
}
