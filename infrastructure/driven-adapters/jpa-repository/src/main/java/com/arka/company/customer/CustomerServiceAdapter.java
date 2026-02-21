package com.arka.company.customer;

import com.arka.dto.out.CustomerSalesReportOut;
import com.arka.gateway.repository.party.CustomerGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomerServiceAdapter implements CustomerGateway {

    private final CustomerRepository repository;


    @Override
    public List<CustomerSalesReportOut>
    getMostFrequentBuyersFromDateRange(Instant since, Instant until) {

        return repository.getMostFrequentBuyersFromDateRange(since, until);
    }
}
