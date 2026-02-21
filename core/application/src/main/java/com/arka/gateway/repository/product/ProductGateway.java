package com.arka.gateway.repository.product;

import com.arka.dto.out.ProductSalesReportOut;
import com.arka.model.product.Product;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProductGateway {

    Product createProduct(Product product);

    Optional<Product> findById(Long id);

    List<ProductSalesReportOut> getTopSellingProductsFromDateRange
            (Instant since, Instant until);
}
