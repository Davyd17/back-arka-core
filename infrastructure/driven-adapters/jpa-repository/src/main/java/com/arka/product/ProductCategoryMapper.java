package com.arka.product;

import com.arka.model.product.ProductCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductCategoryMapper {

    ProductCategory toDomain(ProductCategoryEntity productCategory);

    @Mapping(target = "updatedAt", ignore = true)
    ProductCategoryEntity productCategoryToEntity(ProductCategory domain);

}
