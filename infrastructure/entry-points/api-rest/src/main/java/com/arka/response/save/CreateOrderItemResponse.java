package com.arka.response.save;

import com.arka.dto.out.CreateProductOut;

import java.math.BigDecimal;

public record CreateOrderItemResponse(

        Long id,
        CreateProductResponse product,
        int quantity,
        BigDecimal unitPrice

) {
}
