package com.arka.product;

import com.arka.model.product.ProductCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductCategoryMapper {

    ProductCategory toDomain(ProductCategoryEntity productCategory);

}
