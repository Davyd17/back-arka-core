package com.arka.response.get;

public record WarehouseInventoryResponse(

        Long id,
        int stock,
        WarehouseSummaryOut warehouse,
        ProductSummaryResponse product
) {
}
