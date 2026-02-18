package com.arka.dto.out;

import com.arka.model.product.ProductCategory;
import lombok.Builder;

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

    @Builder
    public CreateProductOut{}
}
