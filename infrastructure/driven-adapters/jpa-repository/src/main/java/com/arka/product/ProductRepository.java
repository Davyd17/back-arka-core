package com.arka.product;

import com.arka.dto.out.ProductSalesReportOut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query(value = """
            SELECT
                p.sku AS sku,
                p.name AS name,
                pc.name AS category,
                SUM(oi.quantity) AS unitsSold
            FROM products p
            JOIN order_items oi ON p.id = oi.product_id
            JOIN orders o ON oi.order_id = o.id
            JOIN product_categories pc ON p.category_id = pc.id
            WHERE o.created_at >= :since AND o.created_at < :until
            GROUP BY p.id, p.sku, p.name, pc.name
            ORDER BY unitsSold DESC
            LIMIT 5
            """,
            nativeQuery = true)
    List<ProductSalesReportOut> getTopSellingProductsFromDateRange(
            @Param("since") Instant since,
            @Param("until") Instant until);
}
