package com.arka.product.mapper;

import com.arka.entities.product.Product;
import com.arka.product.dto.CreateProductOut;
import org.mapstruct.Mapper;

@Mapper
public interface CreateProductOutMapper {

    CreateProductOut toDTO(Product product);
}
