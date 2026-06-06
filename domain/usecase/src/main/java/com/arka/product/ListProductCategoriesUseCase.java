package com.arka.product;

import com.arka.entities.product.ProductCategory;
import com.arka.product.gateway.ProductCategoryGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ListProductCategoriesUseCase {

    private final ProductCategoryGateway categoryGateway;

    public List<ProductCategory> execute(){

        return categoryGateway.getAll();
    }
}
