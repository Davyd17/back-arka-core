package com.arka.gateway;

import com.arka.entities.product.ProductCategory;

import java.util.Optional;

public interface ProductCategoryGateway {

    Optional<ProductCategory> findProductCategoryBySlug(String slug);
}
