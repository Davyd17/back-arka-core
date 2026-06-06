package com.arka.response.get;

import com.arka.inventory.dto.WarehouseSummaryOut;

public record WarehouseInventoryResponse(

        Long id,
        int stock,
        WarehouseSummaryOut warehouse,
        ProductSummaryResponse product
) {
}
