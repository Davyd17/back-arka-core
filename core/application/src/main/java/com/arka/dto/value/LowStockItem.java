package com.arka.dto.value;

import com.arka.dto.out.ProductOut;

public record LowStockItem(

        int stock,
        ProductOut product
) {
}
