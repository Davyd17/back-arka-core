package com.arka.response.get;

import com.arka.dto.out.WarehouseSummaryOut;

public record WarehouseInventoryResponse(

        Long id,
        int stock,
        WarehouseSummaryOut warehouse,
        ProductSummaryResponse product
) {
}
