package com.arka.product.gateway;

import com.arka.model.product.Product;
import com.arka.report.dto.ProductSalesReportOut;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface ProductGateway {

    Product create(Product product);

    Optional<Product> findById(Long id);

    List<ProductSalesReportOut> getTopSellingProductsFromDateRange
            (Instant since, Instant until);
}
