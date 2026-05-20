package com.arka.product.mapper;

import com.arka.product.dto.CreateProductOut;
import com.arka.model.product.Product;
import org.mapstruct.Mapper;

@Mapper
public interface CreateProductOutMapper {

    CreateProductOut toDTO(Product product);
}
