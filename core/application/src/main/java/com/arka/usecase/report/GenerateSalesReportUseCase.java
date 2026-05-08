package com.arka.usecase;

import com.arka.dto.out.CustomerSalesReportOut;
import com.arka.dto.out.ProductSalesReportOut;
import com.arka.dto.out.SalesReportOut;
import com.arka.service.CustomerService;
import com.arka.service.OrderService;
import com.arka.service.ProductService;
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
