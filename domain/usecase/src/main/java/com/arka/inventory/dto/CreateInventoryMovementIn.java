package com.arka.inventory.dto;

import com.arka.enums.InventoryMovementType;

public record CreateInventoryMovementIn(

        InventoryMovementType type,
        int quantity,
        String notes,
        Long productId,
        Long employeeId,
        Long warehouseId

) {

}
