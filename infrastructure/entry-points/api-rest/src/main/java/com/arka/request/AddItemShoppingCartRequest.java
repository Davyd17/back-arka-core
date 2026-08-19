package com.arka.request;

import com.arka.exceptions.Required;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;

public record AddItemShoppingCartRequest(

        @Required(field = "product id")
        @Schema(description = "ID of the product to add", example = "101")
        Long productId,

        @Required(field = "quantity")
        @Min(value = 1, message = "Quantity must be at least 1")
        @Schema(description = "Quantity of items to add", example = "2", minimum = "1")
        int quantity
) {
}
