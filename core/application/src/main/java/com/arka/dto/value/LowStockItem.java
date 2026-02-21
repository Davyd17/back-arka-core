package com.arka.dto.value;

import com.arka.dto.out.ProductSummaryOut;

public record LowStockItem(

        int stock,
        ProductSummaryOut product
) {
}
