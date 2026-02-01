package com.arka.response.get;

import java.time.Instant;

public record ProductCategoryResponse(
        Long id,
        String name,
        Instant createdAt
) {
}
