package com.arka.mapper;

import com.arka.dto.in.CreateProductIn;
import com.arka.dto.in.ProductIdIn;
import com.arka.dto.out.CreateProductOut;
import com.arka.model.product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface CreateProductOutMapper {

    CreateProductOut toDTO(Product product);
}
