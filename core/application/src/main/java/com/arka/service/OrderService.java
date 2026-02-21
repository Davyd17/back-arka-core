package com.arka.service;

import com.arka.dto.value.InstantDateRange;
import com.arka.exceptions.NotFoundException;
import com.arka.gateway.repository.order.OrderGateway;
import com.arka.model.order.Order;
import com.arka.service.util.DateRangeTypeParser;
import com.arka.service.util.DateRangeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.Instant;
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
