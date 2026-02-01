package com.arka.service;

import com.arka.exceptions.NotFoundException;
import com.arka.gateway.ProductCategoryGateway;
import com.arka.model.product.ProductCategory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductCategoryService {

    private final ProductCategoryGateway gateway;

    public ProductCategory getBySlug(String slug){

        return gateway.findProductCategoryBySlug(slug)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Category %s not found", slug)));
    }
}
