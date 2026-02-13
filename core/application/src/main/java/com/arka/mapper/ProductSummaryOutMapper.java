package com.arka.mapper;

import com.arka.dto.out.ProductSummaryOut;
import com.arka.model.product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProductSummaryOutMapper {

    @Mapping(target = "category", source = "domain.category.name")
    ProductSummaryOut toOutDTO(Product domain);
}
