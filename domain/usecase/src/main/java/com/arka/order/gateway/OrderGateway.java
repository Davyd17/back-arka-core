package com.arka.order.gateway;

import com.arka.entities.order.Order;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

public interface OrderGateway {

    Order save(Order newOrder);

    Optional<Order> findById(Long id);

    BigDecimal getTotalRevenueFromDateRange(Instant since, Instant until);
}
