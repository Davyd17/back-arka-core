package com.arka.party.service;

import com.arka.report.dto.CustomerSalesReportOut;
import com.arka.report.dto.InstantDateRange;
import com.arka.report.gateway.CustomerGateway;
import com.arka.util.DateRangeTypeParser;
import com.arka.util.DateRangeValidator;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class CustomerService {

    private final CustomerGateway customerGateway;

    public List<CustomerSalesReportOut> getMostFrequentBuyers
            (LocalDate since, LocalDate until) {

        DateRangeValidator.validate(since, until);

        InstantDateRange parsedRange =
                DateRangeTypeParser.toInstant(since, until);

        return customerGateway
                .getMostFrequentBuyersFromDateRange
                        (parsedRange.start(), parsedRange.end());
    }

}
