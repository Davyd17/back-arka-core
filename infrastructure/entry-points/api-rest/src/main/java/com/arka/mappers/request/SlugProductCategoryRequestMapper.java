package com.arka.mappers.request;

import com.arka.dto.in.SlugProductCategoryIn;
import com.arka.request.SlugProductCategoryRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SlugProductCategoryRequestMapper {

    SlugProductCategoryIn toDomain(SlugProductCategoryRequest request);
}
