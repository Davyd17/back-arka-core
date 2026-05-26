package com.arka.mappers;

import com.arka.product.dto.CreateProductIn;
import com.arka.product.dto.CreateProductOut;
import com.arka.product.dto.ProductSummaryOut;
import com.arka.request.CreateProductRequest;
import com.arka.response.get.ProductSummaryResponse;
import com.arka.response.save.CreateProductResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductRestMapper {

    ProductSummaryResponse toSummaryResponse(ProductSummaryOut output);

    CreateProductIn toInput(CreateProductRequest request);

    CreateProductResponse toCreateResponse(CreateProductOut output);

}
