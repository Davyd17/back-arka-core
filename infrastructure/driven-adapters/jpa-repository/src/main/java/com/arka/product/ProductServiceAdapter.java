package com.arka.product;

import com.arka.gateway.ProductGateway;
import com.arka.model.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceAdapter implements ProductGateway {

    private final ProductRepository repository;
    private final ProductEntityMapper mapper;

    @Override
    public Product createProduct(Product product) {

        if(Objects.nonNull(product)){

            ProductEntity entity = mapper.toEntity(product);
            return mapper.toDomain(repository.save(entity));

        } else throw new IllegalArgumentException(
                "Product can't be null");

    }

    @Override
    public Optional<Product> findById(Long id) {

        return repository.findById(id)
                .map(mapper::toDomain);
    }
}
