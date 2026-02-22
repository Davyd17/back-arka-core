package com.arka.dto.out;


import java.math.BigDecimal;

public record ShoppingCartItemOut(

        Long id,
        int quantity,
        BigDecimal unitPrice,
        ProductSummaryOut product
) {
}
