package com.arka.report.dto;

import java.math.BigDecimal;
import java.util.List;

public record SalesReportData(

        BigDecimal totalSales,
        List<ProductSalesReportOut> topSellingProducts,
        List<CustomerSalesReportOut> mostFrequentBuyers
) {
}
