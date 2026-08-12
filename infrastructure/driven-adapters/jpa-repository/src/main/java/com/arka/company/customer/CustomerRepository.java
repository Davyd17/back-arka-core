package com.arka.company.customer;

import com.arka.company.CompanyEntity;
import com.arka.report.dto.CustomerSalesReportOut;
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
                        comp.name AS companyName,
                        COUNT(o.id) AS totalOrders
                    FROM companies comp
                    JOIN contacts cont ON comp.id = cont.company_id
                    JOIN orders o ON cont.id = o.contact_id
                    WHERE o.created_at >= :since AND o.created_at < :until
                    GROUP BY comp.id, comp.name
                    ORDER BY totalOrders DESC
                    LIMIT 5
                    """,
    nativeQuery = true)
    List<CustomerSalesReportOut> getMostFrequentBuyersFromDateRange
            (@Param("since")Instant since,
             @Param("until") Instant until);

}
