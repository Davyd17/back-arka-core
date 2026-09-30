package com.arka.report.dto;

import java.util.List;

public record LowStockReportData(
        List<Item> items
) {

    public record Item(
            Long productId,
            int stock,
            String sku,
            String name,
            String category
    ){}
}
