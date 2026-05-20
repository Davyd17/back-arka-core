package com.arka.product.service;

import com.arka.report.dto.ProductSalesReportOut;
import com.arka.report.dto.InstantDateRange;
import com.arka.exceptions.NotFoundException;
import com.arka.gateway.product.ProductGateway;
import com.arka.model.product.Product;
import com.arka.util.DateRangeTypeParser;
import com.arka.util.DateRangeValidator;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class ProductService {

    private final ProductGateway gateway;

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
