package com.arka.response.save;

import com.arka.enums.InventoryMovementType;

import java.time.Instant;

public record CreateInventoryMovementResponse(
        Long id,
        InventoryMovementType type,
        int previousStock,
        int newStock,
        String notes,
        Instant registeredAt,
        CreateProductResponse product
) {
}
