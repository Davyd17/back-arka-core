package com.arka.mappers.response;

import com.arka.dto.out.CreateProductOut;
import com.arka.response.save.CreateProductResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreateResponseMapper {

    CreateProductResponse toResponse(CreateProductOut domain);
}
