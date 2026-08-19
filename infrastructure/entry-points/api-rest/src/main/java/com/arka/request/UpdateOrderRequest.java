package com.arka.request;

import com.arka.exceptions.Required;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import java.util.Set;

public record UpdateOrderRequest(

        @Required(field = "notes")
        @Schema(description = "Optional notes for order", example = "Specific case")
        String notes,

        @Required(field = "items")
        @Valid
        Set<Item> items
) {
        @Schema(name = "UpdateOrderItemRequest",
                description = "Add new items to the order or update the quantity of existing ones" )
        public record Item(
                @Required(field = "productId")
                @Schema(example = "3")
                Long productId,

                @Required(field = "quantity")
                @Min(value = 1, message = "Quantity must be at least 1")
                int quantity
        ){}
}
