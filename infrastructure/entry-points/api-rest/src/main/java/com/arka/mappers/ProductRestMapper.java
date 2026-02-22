package com.arka.mappers;

import com.arka.dto.out.ProductSummaryOut;
import com.arka.response.get.ProductSummaryResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductRestMapper {

    ProductSummaryResponse toResponse(ProductSummaryOut outDTO);


}
