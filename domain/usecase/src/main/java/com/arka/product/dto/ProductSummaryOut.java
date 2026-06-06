package com.arka.product.dto;


public record ProductSummaryOut(

        Long id,
        String sku,
        String name,
        String category
) {
}
