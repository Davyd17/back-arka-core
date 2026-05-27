package com.arka.response.save;

import com.arka.enums.InventoryMovementType;
import com.arka.response.get.EmployeeResponse;
import com.arka.response.get.ProductSummaryResponse;

import java.time.Instant;

public record CreateInventoryMovementResponse(
        Long id,
        InventoryMovementType type,
        int quantity,
        int previousStock,
        int newStock,
        String notes,
        Instant registeredAt,
        ProductSummaryResponse product,
        EmployeeResponse employee
) {
}
