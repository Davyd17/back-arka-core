package com.arka.gateway.product;

import com.arka.model.product.ProductCategory;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryGateway {

    Optional<ProductCategory> findById(Long id);

    List<ProductCategory> getAll();

    List<ProductCategory> findAllByIds(List<Long> ids);
}
