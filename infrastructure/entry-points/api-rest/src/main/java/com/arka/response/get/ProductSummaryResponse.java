package com.arka.response.get;

import com.arka.dto.out.ProductCategorySummaryOut;

public record ProductSummaryResponse(

        Long id,
        String sku,
        String name,
        ProductCategorySummaryResponse category
) {
}
