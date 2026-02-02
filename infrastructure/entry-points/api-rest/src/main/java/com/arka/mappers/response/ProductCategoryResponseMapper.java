package com.arka.mappers.response;

import com.arka.model.product.ProductCategory;
import com.arka.response.get.ProductCategoryResponse;
import com.arka.response.save.CreateProductCategoryResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductCategoryResponseMapper {

    ProductCategoryResponse toResponse(ProductCategory domain);
    CreateProductCategoryResponse toSaveResponse(ProductCategory domain);
}
