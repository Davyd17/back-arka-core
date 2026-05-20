package com.arka.product;

import com.arka.product.dto.CreateProductIn;
import com.arka.product.dto.CreateProductOut;
import com.arka.gateway.product.ProductGateway;
import com.arka.product.mapper.CreateProductOutMapper;
import com.arka.mapper.CreateProductOutMapperImpl;
import com.arka.model.product.Product;
import com.arka.model.product.ProductCategory;
import com.arka.product.service.ProductCategoryService;
import com.arka.util.NullValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateProductUseCase {

    private final ProductGateway productGateway;
    private final ProductCategoryService categoryService;

    private final CreateProductOutMapper outMapper =
            new CreateProductOutMapperImpl();

    public CreateProductOut execute(CreateProductIn input){

        NullValidator.validate(input, "input");

            ProductCategory category =
                    categoryService.findById(input.categoryId());

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
}
