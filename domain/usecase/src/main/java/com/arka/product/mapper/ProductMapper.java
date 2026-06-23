package com.arka.product.mapper;

import com.arka.entities.product.Product;
import com.arka.product.dto.CreateProductOut;
import com.arka.product.dto.ProductSummaryOut;
import com.arka.util.pagination.PageWrapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProductMapper {

    @Mapping(target = "category", source = "domain.category.name")
    ProductSummaryOut toSummaryOut(Product domain);

    @Mapping(target = "category", source = "category.name")
    CreateProductOut toCreateOut(Product product);
}
