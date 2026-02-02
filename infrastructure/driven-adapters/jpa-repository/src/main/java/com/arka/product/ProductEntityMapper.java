package com.arka.product;

import com.arka.model.product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductEntityMapper {

    Product toDomain(ProductEntity entity);

    @Mapping(target = "updatedAt", ignore = true)
    ProductEntity toEntity(Product domain);

}
