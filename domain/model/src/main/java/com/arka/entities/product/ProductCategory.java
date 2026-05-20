package com.arka.model.product;

import lombok.*;

@Builder(access = AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
public class ProductCategory {

    private Long id;
    private String name;
    private String slug;

    public static ProductCategory create(
            String name
    ) {
        return ProductCategory.builder()
                .name(name)
                .slug(name.toLowerCase().replace(" ", "-"))
                .build();
    }
}
