package com.arka.mappers.request;

import com.arka.dto.in.CreateProduct;
import com.arka.request.CreateProductRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreateProductRequestMapper {

    CreateProduct toDomain(CreateProductRequest request);
}
