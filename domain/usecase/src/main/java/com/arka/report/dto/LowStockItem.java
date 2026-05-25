package com.arka.report.dto;

import com.arka.product.dto.ProductSummaryOut;

public record LowStockItem(

        int stock,
        ProductSummaryOut product
) {
}
