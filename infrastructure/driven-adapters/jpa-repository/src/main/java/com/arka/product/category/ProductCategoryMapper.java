package com.arka.product.category;

import com.arka.entities.product.ProductCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ProductCategoryMapper {

    ProductCategory toDomain(ProductCategoryEntity productCategory);

    @Mappings({
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "createdAt", ignore = true)
    })
    ProductCategoryEntity productCategoryToEntity(ProductCategory domain);

}
