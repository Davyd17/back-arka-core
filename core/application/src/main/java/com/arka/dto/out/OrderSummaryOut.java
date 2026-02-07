package com.arka.dto.out;

import com.arka.model.enums.OrderType;

public record OrderSummaryOut(
        Long id,
        String number,
        OrderType type
) {
}
