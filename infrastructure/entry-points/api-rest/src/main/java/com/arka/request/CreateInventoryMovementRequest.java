package com.arka.request;

import com.arka.exceptions.Required;
import com.arka.enums.InventoryMovementType;
import jakarta.validation.constraints.Min;

public record CreateInventoryMovementRequest(

        @Required(field = "type")
        InventoryMovementType type,

        @Required(field = "quantity")
        @Min(value = 1, message = "Quantity must be at least 1")
        int quantity,

        String notes,

        @Required(field = "productId")
        Long productId,

        @Required(field = "employeeId")
        Long employeeId,

        @Required(field = "warehouseId")
        Long warehouseId
) {
}
