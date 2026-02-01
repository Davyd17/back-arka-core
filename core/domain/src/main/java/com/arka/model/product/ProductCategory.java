package com.arka.model.product;

import lombok.*;

import java.time.Instant;

@Builder
public record ProductCategory(
        Long id,
        String name,
        String slug,
        Instant createdAt) {

    public static ProductCategory create(
            String name,
            String slug
    ) {
        return ProductCategory.builder()
                .name(name)
                .slug(slug)
                .createdAt(Instant.now())
                .build();
    }
}
