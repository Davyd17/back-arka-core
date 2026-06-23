package com.arka.product;

import com.arka.entities.product.Product;
import com.arka.report.dto.ProductSalesReportOut;
import com.arka.product.gateway.ProductGateway;
import com.arka.util.PageableMapper;
import com.arka.util.pagination.PageWrapper;
import com.arka.util.pagination.PageableIn;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceAdapter implements ProductGateway {

    private final ProductRepository repository;
    private final ProductEntityMapper mapper;

    @Override
    public Product create(Product product) {

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

    @Override
    public List<ProductSalesReportOut> getTopSellingProductsFromDateRange(Instant since, Instant until) {
        return repository.getTopSellingProductsFromDateRange(since, until)
                .stream()
                .toList();
    }

    @Override
    public PageWrapper<Product> findAll(PageableIn input) {

        Page<ProductEntity> result = repository.findAll(
                PageableMapper.toPageable(input));

        return PageableMapper.toPageWrapper(result, mapper::toDomain);
    }
}
