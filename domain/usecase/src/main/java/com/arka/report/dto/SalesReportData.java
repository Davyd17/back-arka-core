package com.arka.report.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

public record SalesReportData(

        BigDecimal totalSales,
        List<ProductSalesReportOut> topSellingProducts,
        List<CustomerSalesReportOut> mostFrequentBuyers
) {

    @Builder(toBuilder = true)
    public SalesReportData {

    }
}
