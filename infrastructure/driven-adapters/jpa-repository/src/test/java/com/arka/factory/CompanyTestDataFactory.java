package com.arka.factory;

import com.arka.company.CompanyEntity;
import com.arka.enums.CompanyRelationType;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.Instant;

public class CompanyTestDataFactory {

    private final TestEntityManager entityManager;

    public CompanyTestDataFactory(TestEntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public CompanyEntity createCompany() {
        CompanyEntity company = CompanyEntity.builder()
                .name("Arka Corp")
                .relation(CompanyRelationType.OWN)
                .createdAt(Instant.now())
                .build();

        return entityManager.persistAndFlush(company);
    }

    public CompanyEntity createCompany(String name) {
        CompanyEntity company = CompanyEntity.builder()
                .name(name)
                .relation(CompanyRelationType.CUSTOMER)
                .createdAt(Instant.now())
                .build();

        return entityManager.persistAndFlush(company);
    }
}
