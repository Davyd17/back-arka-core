package com.arka.order.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {

    @Query(value =
            """
                    SELECT COALESCE(SUM(oi.quantity), 0)
                       FROM order_items oi
                       JOIN orders o ON oi.order_id = o.id
                       WHERE oi.product_id = :productId
                       AND o.created_at >= :since
                       AND o.created_at <= :until
                    """,
            nativeQuery = true
    )
    int getTotalUnitsSoldByProductIdFromDateRange
            (@Param("productId") Long productId,
             @Param("since") LocalDate since,
             @Param("until") LocalDate until);
}
