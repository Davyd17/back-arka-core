package com.arka.report;

import com.arka.report.dto.CustomerSalesReportOut;
import com.arka.report.dto.ProductSalesReportOut;
import com.arka.report.dto.SalesReportOut;
import com.arka.party.service.CustomerService;
import com.arka.order.service.OrderService;
import com.arka.product.service.ProductService;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class GenerateSalesReportUseCase {

    private final OrderService orderService;
    private final CustomerService customerService;
    private final ProductService productService;

    public SalesReportOut execute(){

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

        return new SalesReportOut(
                totalRevenueLast7Days,
                topLast7DaysSellingProducts,
                must7DaysFrequentBuyers
        );
    }
}
