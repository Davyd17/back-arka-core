package com.arka.company.customer;

import com.arka.company.CompanyEntity;
import com.arka.factory.CompanyTestDataFactory;
import com.arka.factory.ContactTestDataFactory;
import com.arka.factory.OrderTestDataFactory;
import com.arka.information.contact.ContactEntity;
import com.arka.report.dto.CustomerSalesReportOut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(CustomerServiceAdapter.class)
@ActiveProfiles("test")
class CustomerServiceAdapterTest {

    @Autowired
    private CustomerServiceAdapter customerServiceAdapter;

    @Autowired
    private TestEntityManager entityManager;

    private CompanyTestDataFactory companyTestDataFactory;
    private ContactTestDataFactory contactTestDataFactory;
    private OrderTestDataFactory orderTestDataFactory;

    @BeforeEach
    void setUp() {
        companyTestDataFactory = new CompanyTestDataFactory(entityManager);
        contactTestDataFactory = new ContactTestDataFactory(entityManager);
        orderTestDataFactory = new OrderTestDataFactory(entityManager);
    }

    @Test
    void shouldReturnMostFrequentBuyersFromDateRange() {
        // Given
        CompanyEntity company = companyTestDataFactory.createCompany("Arka Corp");
        ContactEntity contact = contactTestDataFactory.createContact(company);

        Instant orderDate = Instant.parse("2026-06-15T10:00:00Z");
        orderTestDataFactory.createOrder(contact, orderDate);

        Instant since = Instant.parse("2026-01-01T00:00:00Z");
        Instant until = Instant.parse("2026-12-31T23:59:59Z");

        // When
        List<CustomerSalesReportOut> result = customerServiceAdapter
                .getMostFrequentBuyersFromDateRange(since, until);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);

        CustomerSalesReportOut topBuyer = result.get(0);
        assertThat(topBuyer.companyName()).isEqualTo("Arka Corp");
        assertThat(topBuyer.totalOrders()).isEqualTo(1L);
    }

    @Test
    void shouldReturnEmptyListWhenNoOrdersFoundInDateRange() {
        // Given
        CompanyEntity company = companyTestDataFactory.createCompany("Arka Corp");
        ContactEntity contact = contactTestDataFactory.createContact(company);

        Instant orderDate = Instant.parse("2026-06-15T10:00:00Z");
        orderTestDataFactory.createOrder(contact, orderDate);

        Instant since = Instant.parse("2020-01-01T00:00:00Z");
        Instant until = Instant.parse("2020-12-31T23:59:59Z");

        // When
        List<CustomerSalesReportOut> result = customerServiceAdapter
                .getMostFrequentBuyersFromDateRange(since, until);

        // Then
        assertThat(result).isEmpty();
    }}
