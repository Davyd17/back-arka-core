package com.arka.cart.dto;


import com.arka.product.dto.ProductSummaryOut;

import java.math.BigDecimal;

public record ShoppingCartItemOut(

        Long id,
        int quantity,
        BigDecimal unitPrice,
        ProductSummaryOut product
) {
}
