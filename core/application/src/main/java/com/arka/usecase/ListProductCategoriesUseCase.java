package com.arka.usecase;

import com.arka.gateway.repository.product.ProductCategoryGateway;
import com.arka.model.product.ProductCategory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ListProductCategoriesUseCase {

    private final ProductCategoryGateway categoryGateway;

    public List<ProductCategory> execute(){

        return categoryGateway.getAll();
    }
}
