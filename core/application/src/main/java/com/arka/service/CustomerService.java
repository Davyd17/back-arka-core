package com.arka.service;

import com.arka.dto.out.CustomerSalesReportOut;
import com.arka.dto.value.InstantDateRange;
import com.arka.gateway.repository.order.OrderGateway;
import com.arka.gateway.repository.party.CustomerGateway;
import com.arka.service.util.DateRangeTypeParser;
import com.arka.service.util.DateRangeValidator;
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
