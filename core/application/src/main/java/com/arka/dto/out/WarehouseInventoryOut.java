package com.arka.dto.out;

public record WarehouseInventoryOut(

        Long id,
        int stock,
        WarehouseSummaryOut warehouse,
        ProductSummaryOut product
) {
}
