package com.arka.mappers;

import com.arka.model.product.ProductCategory;
import com.arka.response.ProductCategoryResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductCategoryResponseMapper {

    ProductCategoryResponse toResponse(ProductCategory productCategory);
}
