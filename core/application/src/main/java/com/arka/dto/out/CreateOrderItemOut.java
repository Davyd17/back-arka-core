package com.arka.dto.out;

import java.math.BigDecimal;

public record CreateOrderItemOut(

        Long id,
        CreateProductOut product,
        int quantity,
        BigDecimal unitPrice
) {
}
