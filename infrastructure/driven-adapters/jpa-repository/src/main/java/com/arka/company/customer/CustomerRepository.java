package com.arka.company.customer;

import com.arka.company.CompanyEntity;
import com.arka.dto.out.CustomerSalesReportOut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<CompanyEntity, Long> {

    @Query(value =
            """
                    SELECT
                        c.name AS companyName,
                        COUNT(o.id) AS totalOrders
                    FROM companies c
                    JOIN orders o ON c.id = o.company_id
                    WHERE o.created_at >= :since AND o.created_at < :until
                    GROUP BY c.id, c.name
                    ORDER BY totalOrders DESC
                    LIMIT 5
                    """,
    nativeQuery = true)
    List<CustomerSalesReportOut> getMostFrequentBuyersFromDateRange
            (@Param("since")Instant since,
             @Param("until") Instant until);

}
