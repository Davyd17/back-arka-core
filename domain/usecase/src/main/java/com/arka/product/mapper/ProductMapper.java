package com.arka.product.mapper;

import com.arka.entities.product.Product;
import com.arka.product.dto.ProductOut;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProductMapper {

    @Mapping(target = "category", source = "domain.category.name")
    ProductOut toOut(Product domain);
}
