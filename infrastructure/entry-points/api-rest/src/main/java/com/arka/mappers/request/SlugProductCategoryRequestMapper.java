package com.arka.mappers.request;

import com.arka.dto.request.SlugProductCategory;
import com.arka.request.SlugProductCategoryRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SlugProductCategoryRequestMapper {

    SlugProductCategory toDomain(SlugProductCategoryRequest request);
}
