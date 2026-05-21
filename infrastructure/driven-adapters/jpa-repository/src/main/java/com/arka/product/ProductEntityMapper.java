package com.arka.product;

import com.arka.entities.product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ProductEntityMapper {

    Product toDomain(ProductEntity entity);

    @Mappings({
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "createdAt", ignore = true)
    })
    ProductEntity toEntity(Product domain);

}
