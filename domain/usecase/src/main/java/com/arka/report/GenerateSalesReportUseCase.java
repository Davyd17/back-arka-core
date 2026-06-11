package com.arka.report;

import com.arka.report.dto.CustomerSalesReportOut;
import com.arka.report.dto.ProductSalesReportOut;
import com.arka.report.dto.SalesReportData;
import com.arka.party.service.CustomerService;
import com.arka.order.service.OrderService;
import com.arka.product.service.ProductService;
import com.arka.report.gateway.ExportGateway;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class GenerateSalesReportUseCase {

    private final OrderService orderService;
    private final CustomerService customerService;
    private final ProductService productService;

    private final ExportGateway exportGateway;

    public byte[] execute(ExportFormat format){

        LocalDate sevenDaysAgo = LocalDate.now().minusDays(7);
        LocalDate now = LocalDate.now();

        List<ProductSalesReportOut> topLast7DaysSellingProducts = productService
                .getTopSellingProducts(
                        sevenDaysAgo, now);

        List<CustomerSalesReportOut> must7DaysFrequentBuyers = customerService
                .getMostFrequentBuyers(
                        sevenDaysAgo, now);

        BigDecimal totalRevenueLast7Days = orderService
                .getTotalRevenue(
                        sevenDaysAgo, now);

        SalesReportData data = new SalesReportData(
                totalRevenueLast7Days,
                topLast7DaysSellingProducts,
                must7DaysFrequentBuyers
        );

        return exportGateway.export(data, format);
    }
}
