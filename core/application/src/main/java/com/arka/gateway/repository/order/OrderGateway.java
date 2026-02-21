package com.arka.gateway.repository.order;

import com.arka.model.order.Order;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

public interface OrderGateway {

    Order createOrder(Order newOrder);

    Optional<Order> findById(Long id);

    Order update(Order order);

    BigDecimal getTotalRevenueFromDateRange(Instant since, Instant until);
}
