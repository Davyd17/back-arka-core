package com.arka.inventory.dto;

import com.arka.party.dto.EmployeeOut;
import com.arka.product.dto.ProductOut;
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
