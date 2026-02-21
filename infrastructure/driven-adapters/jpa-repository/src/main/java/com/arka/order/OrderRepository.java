package com.arka.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.Instant;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query(value = """
            SELECT COALESCE(SUM(o.total_price), 0)
            FROM orders o
            WHERE o.created_at >= :since
            AND o.created_at < :until
            """,
    nativeQuery = true)
    BigDecimal getTotalRevenueFromDateRange(
            @Param("since") Instant since,
            @Param("until") Instant until);

}
