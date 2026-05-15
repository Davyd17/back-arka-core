package com.arka.dto.out;

import com.arka.enums.InventoryMovementType;

import java.time.Instant;

public record CreateInventoryMovementOut(
        Long id,
        InventoryMovementType type,
        int previousStock,
        int newStock,
        String notes,
        Instant registeredAt,
        ProductOut product,
        EmployeeOut employee
) {
}
