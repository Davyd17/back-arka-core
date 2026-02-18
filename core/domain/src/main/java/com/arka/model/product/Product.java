package com.arka.model.product;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Product {
    private Long id;
    private String sku;
    private String name;
    private String description;
    private BigDecimal basePrice;
    private Map<String, Object> attributes;
    private ProductCategory category;
    private boolean active;
    private Instant createdAt;

    public static Product create(
            String sku,
            String name,
            String description,
            BigDecimal basePrice,
            Map<String, Object> attributes,
            ProductCategory category,
            Instant createdAt
    ){

        return Product.builder()
                .sku(sku)
                .name(name)
                .description(description)
                .basePrice(basePrice)
                .attributes(attributes)
                .createdAt(createdAt)
                .category(category)
                .active(true)
                .build();
    }
}
