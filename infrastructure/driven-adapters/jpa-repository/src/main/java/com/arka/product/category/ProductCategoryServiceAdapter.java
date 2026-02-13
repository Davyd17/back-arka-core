package com.arka.product.category;

import com.arka.model.product.ProductCategory;
import com.arka.repository.ProductCategoryGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductCategoryServiceAdapter implements ProductCategoryGateway {

    private final ProductCategoryRepository repository;
    private final ProductCategoryMapper mapper;

    @Override
    public Optional<ProductCategory> findProductCategoryBySlug(String slug) {
        return repository.findBySlug(slug)
                .map(mapper::toDomain);
    }

    @Override
    public List<ProductCategory> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }
}
