package com.arka.factory;

import com.arka.employee.EmployeeEntity;
import com.arka.information.contact.ContactEntity;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.Instant;

public class EmployeeTestDataFactory {

    private final TestEntityManager entityManager;
    private final ContactTestDataFactory contactTestDataFactory;

    public EmployeeTestDataFactory(TestEntityManager entityManager) {
        this.entityManager = entityManager;
        this.contactTestDataFactory = new ContactTestDataFactory(entityManager);
    }

    public EmployeeEntity createEmployee() {
        ContactEntity contact = contactTestDataFactory.createContact();

        EmployeeEntity employee = EmployeeEntity.builder()
                .code((int) (System.currentTimeMillis() % 100000))
                .contact(contact)
                .createdAt(Instant.now())
                .build();

        return entityManager.persistAndFlush(employee);
    }
}
