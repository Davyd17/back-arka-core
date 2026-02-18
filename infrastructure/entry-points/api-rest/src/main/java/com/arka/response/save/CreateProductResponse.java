package com.arka.response.save;

import com.arka.response.get.ProductCategoryResponse;

import java.math.BigDecimal;
import java.util.Map;

public record CreateProductResponse(
        Long id,
        String sku,
        String name,
        String description,
        BigDecimal basePrice,
        Map<String, Object>attributes,
        ProductCategoryResponse category
) {
}
