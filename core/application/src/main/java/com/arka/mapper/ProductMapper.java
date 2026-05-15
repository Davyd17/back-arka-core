package com.arka.mapper;

import com.arka.dto.out.ProductOut;
import com.arka.model.product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProductMapper {

    @Mapping(target = "category", source = "domain.category.name")
    ProductOut toOut(Product domain);
}
