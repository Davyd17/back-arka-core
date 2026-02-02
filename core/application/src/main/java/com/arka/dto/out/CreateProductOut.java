package com.arka.dto.out;

import com.arka.model.product.ProductCategory;
import lombok.Builder;

import java.util.Map;

public record CreateProductOut(
        Long id,
        String sku,
        String name,
        String description,
        Map<String, Object>attributes,
        ProductCategory category
) {

    @Builder
    public CreateProductOut{}
}
