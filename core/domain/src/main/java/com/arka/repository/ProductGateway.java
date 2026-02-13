package com.arka.repository;

import com.arka.model.product.Product;

import java.util.Optional;

public interface ProductGateway {

    Product createProduct(Product product);

    Optional<Product> findById(Long id);
}
