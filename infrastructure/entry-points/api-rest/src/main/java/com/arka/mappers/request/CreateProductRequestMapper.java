package com.arka.mappers.request;

import com.arka.dto.in.CreateProductIn;
import com.arka.request.CreateProductRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreateProductRequestMapper {

    CreateProductIn toDomain(CreateProductRequest request);
}
