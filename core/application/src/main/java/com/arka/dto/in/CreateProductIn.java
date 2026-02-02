package com.arka.dto.in;

import java.util.Map;

public record CreateProductIn(
        String sku,
        String name,
        String description,
        Map<String, Object> attributes,
        SlugProductCategoryIn slugCategory
) {
}
