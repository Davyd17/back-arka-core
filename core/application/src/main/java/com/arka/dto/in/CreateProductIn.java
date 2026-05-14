package com.arka.dto.in;

import jakarta.annotation.Nullable;

import java.math.BigDecimal;
import java.util.Map;

public record CreateProductIn(
        String sku,
        String name,
        Long categoryId,
        @Nullable String description,
        BigDecimal basePrice,
        @Nullable Map<String, Object> attributes
) {
}
