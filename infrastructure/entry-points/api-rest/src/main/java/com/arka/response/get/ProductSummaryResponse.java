package com.arka.response.get;

public record ProductSummaryResponse(

        Long id,
        String sku,
        String name,
        String category
) {
}
