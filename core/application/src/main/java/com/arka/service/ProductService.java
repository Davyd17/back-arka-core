package com.arka.service;

import com.arka.exceptions.NotFoundException;
import com.arka.gateway.ProductGateway;
import com.arka.model.product.Product;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductService {

    private final ProductGateway gateway;

    public Product findById(Long id){

        return gateway.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Product with id %s not found", id)
                ));
    }
}
