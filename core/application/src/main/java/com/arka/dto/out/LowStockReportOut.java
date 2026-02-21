package com.arka.dto.out;

import com.arka.dto.value.LowStockItem;

import java.util.List;

public record LowStockReportOut(
        List<LowStockItem> items
) {
}
