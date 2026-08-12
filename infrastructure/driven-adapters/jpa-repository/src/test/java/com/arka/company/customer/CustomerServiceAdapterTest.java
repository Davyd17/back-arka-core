package com.arka.company.customer;

import com.arka.report.dto.CustomerSalesReportOut;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(CustomerServiceAdapter.class)
@ActiveProfiles("test")
class CustomerServiceAdapterTest {

    @Autowired
    private CustomerServiceAdapter customerServiceAdapter;

    @Test
    void shouldReturnMostFrequentBuyersFromDateRange() {
        // Given
        Instant since = Instant.parse("2026-01-01T00:00:00Z");
        Instant until = Instant.parse("2026-12-31T23:59:59Z");

        // When
        List<CustomerSalesReportOut> result = customerServiceAdapter.getMostFrequentBuyersFromDateRange(since, until);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);

        CustomerSalesReportOut topBuyer = result.get(0);
        assertThat(topBuyer.companyName()).isEqualTo("Arka Corp");
        assertThat(topBuyer.totalOrders()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Should return empty list when date range yields no matching orders")
    void shouldReturnEmptyListWhenNoOrdersFoundInDateRange() {
        // Given
        Instant since = Instant.parse("2020-01-01T00:00:00Z");
        Instant until = Instant.parse("2020-12-31T23:59:59Z");

        // When
        List<CustomerSalesReportOut> result = customerServiceAdapter.getMostFrequentBuyersFromDateRange(since, until);

        // Then
        assertThat(result).isEmpty();
    }
}
