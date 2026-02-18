package com.arka.dto.in;

import java.math.BigDecimal;
import java.util.Map;

public record CreateProductIn(
        String sku,
        String name,
        String description,
        BigDecimal basePrice,
        Map<String, Object> attributes,
        SlugProductCategoryIn slugCategory
) {
}
