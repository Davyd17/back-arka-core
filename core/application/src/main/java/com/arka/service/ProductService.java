package com.arka.service;

import com.arka.dto.out.ProductSalesReportOut;
import com.arka.dto.value.InstantDateRange;
import com.arka.exceptions.NotFoundException;
import com.arka.mapper.ProductSummaryOutMapper;
import com.arka.mapper.ProductSummaryOutMapperImpl;
import com.arka.gateway.repository.product.ProductGateway;
import com.arka.model.product.Product;
import com.arka.service.util.DateRangeTypeParser;
import com.arka.service.util.DateRangeValidator;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class ProductService {

    private final ProductGateway gateway;

    private final ProductSummaryOutMapper productSummaryMapper =
            new ProductSummaryOutMapperImpl();

    public Product findById(Long id){

        return gateway.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Product with id %s not found", id)
                ));
    }

    public List<ProductSalesReportOut> getTopSellingProducts
            (LocalDate since, LocalDate until) {

        DateRangeValidator.validate(since, until);

        InstantDateRange parsedRange =
                DateRangeTypeParser.toInstant(since, until);

        return gateway
                .getTopSellingProductsFromDateRange
                        (parsedRange.start(), parsedRange.end());
    }
}
