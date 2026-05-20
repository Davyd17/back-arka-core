package com.arka.order.dto;

import com.arka.product.dto.CreateProductOut;

import java.math.BigDecimal;

public record CreateOrderItemOut(

        Long id,
        CreateProductOut product,
        int quantity,
        BigDecimal unitPriceSnapshot,
        BigDecimal totalPrice
) {
}
