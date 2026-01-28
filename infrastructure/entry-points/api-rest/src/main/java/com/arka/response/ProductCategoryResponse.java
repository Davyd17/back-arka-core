package com.arka.response;

import java.time.Instant;

public record ProductCategoryResponse(
        Long id,
        String name,
        Instant createdAt,
        Instant updatedAt
) {
}
