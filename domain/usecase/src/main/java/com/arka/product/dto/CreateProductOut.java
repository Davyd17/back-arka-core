package com.arka.product.dto;

import com.arka.entities.product.ProductCategory;

import java.math.BigDecimal;
import java.util.Map;

public record CreateProductOut(
        Long id,
        String sku,
        String name,
        String description,
        BigDecimal basePrice,
        Map<String, Object>attributes,
        ProductCategory category
) {
}
