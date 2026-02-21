package com.arka.gateway.repository.party;

import com.arka.dto.out.CustomerSalesReportOut;

import java.time.Instant;
import java.util.List;

public interface CustomerGateway{

    List<CustomerSalesReportOut> getMostFrequentBuyersFromDateRange
            (Instant since, Instant until);
}
