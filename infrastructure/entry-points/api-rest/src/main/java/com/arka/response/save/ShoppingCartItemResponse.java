package com.arka.response.save;

import com.arka.response.get.ProductSummaryResponse;

import java.math.BigDecimal;

public record ShoppingCartItemResponse(
        Long id,
        int quantity,
        BigDecimal unitPrice,
        ProductSummaryResponse product
) {
}
