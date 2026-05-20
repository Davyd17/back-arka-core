package com.arka.report.gateway;

import com.arka.report.dto.CustomerSalesReportOut;

import java.time.Instant;
import java.util.List;

public interface CustomerGateway{

    List<CustomerSalesReportOut> getMostFrequentBuyersFromDateRange
            (Instant since, Instant until);
}
