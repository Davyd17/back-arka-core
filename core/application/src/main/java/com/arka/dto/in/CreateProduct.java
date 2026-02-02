package com.arka.dto.in;

import java.util.Map;

public record CreateProduct(
        String sku,
        String name,
        String description,
        Map<String, Object> attributes,
        SlugProductCategory slugCategory
) {
}
