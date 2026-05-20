package com.arka.report.dto;

import com.arka.product.dto.ProductOut;

public record LowStockItem(

        int stock,
        ProductOut product
) {
}
