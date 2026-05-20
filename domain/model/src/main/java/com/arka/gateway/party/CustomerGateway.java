package com.arka.gateway.party;

import com.arka.dto.out.report.CustomerSalesReportOut;

import java.time.Instant;
import java.util.List;

public interface CustomerGateway{

    List<CustomerSalesReportOut> getMostFrequentBuyersFromDateRange
            (Instant since, Instant until);
}
