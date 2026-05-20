package com.arka.order.service;

import com.arka.report.dto.InstantDateRange;
import com.arka.exceptions.NotFoundException;
import com.arka.gateway.order.OrderGateway;
import com.arka.model.order.Order;
import com.arka.util.DateRangeTypeParser;
import com.arka.util.DateRangeValidator;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
public class OrderService {

    private final OrderGateway orderGateway;

    public Order findById(Long id){

        return orderGateway.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Order with id %s not found", id)
                ));

    }

    public BigDecimal getTotalRevenue(LocalDate since, LocalDate until) {

        DateRangeValidator.validate(since, until);

        InstantDateRange parsedRange =
                DateRangeTypeParser.toInstant(since, until);

        return orderGateway
                .getTotalRevenueFromDateRange
                        (parsedRange.start(), parsedRange.end());
    }


}
