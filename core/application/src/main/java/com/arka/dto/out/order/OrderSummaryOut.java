package com.arka.dto.out;

import com.arka.enums.OrderType;

public record OrderSummaryOut(
        Long id,
        String number,
        OrderType type
) {
}
