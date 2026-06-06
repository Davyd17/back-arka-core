package com.arka.product.category;

import com.arka.entities.product.ProductCategory;
import com.arka.product.gateway.ProductCategoryGateway;
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
    public Optional<ProductCategory> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<ProductCategory> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<ProductCategory> findAllByIds(List<Long> ids) {
        return repository.findAllByIdIn(ids).stream()
                .map(mapper::toDomain)
                .toList();

    }
}
