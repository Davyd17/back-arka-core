package com.arka.product;

import com.arka.entities.product.Product;
import com.arka.product.dto.ProductSummaryOut;
import com.arka.product.gateway.ProductGateway;
import com.arka.product.mapper.ProductMapper;
import com.arka.util.NullValidator;
import com.arka.util.pagination.PageWrapper;
import com.arka.util.pagination.PageableIn;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;

import java.util.List;

@RequiredArgsConstructor
public class ListAllProductsUseCase {

    private final ProductGateway productGateway;
    private final ProductMapper mapper =
            Mappers.getMapper(ProductMapper.class);

    public PageWrapper<ProductSummaryOut> execute(PageableIn pageable){

        NullValidator.validate(pageable, "Pageable");

        return productGateway.findAll(pageable).map(mapper::toSummaryOut);
    }
}
