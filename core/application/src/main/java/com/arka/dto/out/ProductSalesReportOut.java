package com.arka.dto.out;

public record ProductSalesReportOut(

        String sku,
        String name,
        String category,
        Long unitsSold
) {
}
