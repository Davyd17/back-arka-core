package com.arka.factory;

import com.arka.product.ProductEntity;
import com.arka.product.category.ProductCategoryEntity;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;

public class ProductTestDataFactory {

    private final TestEntityManager entityManager;

    public ProductTestDataFactory(TestEntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public ProductCategoryEntity createCategory() {
        ProductCategoryEntity category = ProductCategoryEntity.builder()
                .name("Electronics")
                .slug("electronics-" + System.currentTimeMillis())
                .createdAt(Instant.now())
                .build();
        return entityManager.persistAndFlush(category);
    }

    public ProductEntity createProduct() {
        return createProduct(createCategory());
    }

    public ProductEntity createProduct(ProductCategoryEntity category) {
        ProductEntity product = ProductEntity.builder()
                .sku("PROD-" + System.currentTimeMillis())
                .name("Test Product " + System.currentTimeMillis())
                .description("Standard Test Product Description")
                .basePrice(new BigDecimal("99.99"))
                .attributes(new HashMap<>())
                .active(true)
                .category(category)
                .createdAt(Instant.now())
                .build();
        return entityManager.persistAndFlush(product);
    }
}
