package com.arka.response.save;

import com.arka.enums.InventoryMovementType;
import com.arka.party.dto.EmployeeOut;
import com.arka.product.dto.ProductOut;

import java.time.Instant;

public record CreateInventoryMovementResponse(
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
