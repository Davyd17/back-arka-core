package com.arka.usecase;

import com.arka.dto.in.CreateProductIn;
import com.arka.dto.out.CreateProductOut;
import com.arka.gateway.repository.product.ProductGateway;
import com.arka.mapper.CreateProductOutMapper;
import com.arka.mapper.CreateProductOutMapperImpl;
import com.arka.model.product.Product;
import com.arka.model.product.ProductCategory;
import com.arka.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.Objects;

@RequiredArgsConstructor
public class CreateProductUseCase {

    private final ProductGateway productGateway;
    private final ProductCategoryService categoryService;

    private final CreateProductOutMapper outMapper =
            new CreateProductOutMapperImpl();

    public CreateProductOut execute(CreateProductIn request){

        if(Objects.nonNull(request)){

            ProductCategory category = categoryService
                    .getBySlug(request.slugCategory().slug());

            return outMapper.toDTO(productGateway.createProduct(
                    Product.create(
                            request.sku(),
                            request.name(),
                            request.description(),
                            request.basePrice(),
                            request.attributes(),
                            category,
                            Instant.now()
                    )
            ));

        } else throw new IllegalArgumentException(
                "Request product can't be null"
        );

    }
}
