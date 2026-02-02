package com.arka.mapper;

import com.arka.dto.out.CreateProductOut;
import com.arka.model.product.Product;
import org.mapstruct.Mapper;

@Mapper
public interface CreateProductOutMapper {

    CreateProductOut toDTO(Product product);
}
