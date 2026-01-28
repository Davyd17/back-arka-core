package com.arka.product;

import com.arka.entities.product.ProductCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductCategoryMapper {

    ProductCategory toDomain(ProductCategoryEntity productCategory);

}
