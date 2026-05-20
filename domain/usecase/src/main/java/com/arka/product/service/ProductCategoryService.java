package com.arka.product.service;

import com.arka.exceptions.NotFoundException;
import com.arka.product.gateway.ProductCategoryGateway;
import com.arka.model.product.ProductCategory;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class ProductCategoryService {

    private final ProductCategoryGateway gateway;

    public ProductCategory findById(Long id){
        return gateway.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Category with id %s not found", id)));
    }

    public List<ProductCategory> findAllByIds(Set<Long> ids){

        List<ProductCategory> foundCategories =
                gateway.findAllByIds(new ArrayList<>(ids));

        if(ids.size() != foundCategories.size())
            throw new NotFoundException("one or more categories not found");

        return foundCategories;
    }

}
