package com.arka.gateway.repository.product;

import com.arka.model.product.ProductCategory;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryGateway {

    Optional<ProductCategory> findProductCategoryBySlug(String slug);

    List<ProductCategory> getAll();
}
