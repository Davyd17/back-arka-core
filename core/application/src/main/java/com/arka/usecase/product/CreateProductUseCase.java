package com.arka.usecase.product;

import com.arka.dto.in.CreateProductIn;
import com.arka.dto.out.CreateProductOut;
import com.arka.exceptions.NotFoundException;
import com.arka.gateway.repository.product.ProductCategoryGateway;
import com.arka.gateway.repository.product.ProductGateway;
import com.arka.mapper.CreateProductOutMapper;
import com.arka.mapper.CreateProductOutMapperImpl;
import com.arka.model.product.Product;
import com.arka.model.product.ProductCategory;
import com.arka.service.ProductCategoryService;
import com.arka.util.NullValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateProductUseCase {

    private final ProductGateway productGateway;
    private final ProductCategoryService categoryService;
    private final ProductCategoryGateway categoryGateway;

    private final CreateProductOutMapper outMapper =
            new CreateProductOutMapperImpl();

    public CreateProductOut execute(CreateProductIn input){

        NullValidator.validate(input, "input");

            ProductCategory category =
                    getExistingCategory(input.categoryId());

            Product product = Product.create(
                    input.sku(),
                    input.name(),
                    input.description(),
                    input.basePrice(),
                    category);

            if(input.attributes() != null && !input.attributes().isEmpty())
                 input.attributes().forEach(product::addAttribute);

            return outMapper.toDTO(productGateway.create(product));

    }

    private ProductCategory getExistingCategory(Long id){
        return categoryGateway.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Category with id %d not found", id)));
    }
}
