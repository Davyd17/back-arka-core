package com.arka.dto.out;


public record ProductSummaryOut(

        Long id,
        String sku,
        String name,
        String category
) {
}
