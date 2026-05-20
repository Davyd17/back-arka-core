package com.arka.report.dto;

public record ProductSalesReportOut(

        String sku,
        String name,
        String category,
        Long unitsSold
) {
}
