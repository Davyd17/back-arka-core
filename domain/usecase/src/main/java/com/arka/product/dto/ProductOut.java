package com.arka.product.dto;


public record ProductOut(

        Long id,
        String sku,
        String name,
        String category
) {
}
