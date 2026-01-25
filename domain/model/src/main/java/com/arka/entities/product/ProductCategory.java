package com.arka.entities.product;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ProductCategory {

    private Long id;
    private String name;
    private Instant createdAt;
    private Instant updatedAt;

    public ProductCategory() {
        this.createdAt = Instant.now();
    }
}
