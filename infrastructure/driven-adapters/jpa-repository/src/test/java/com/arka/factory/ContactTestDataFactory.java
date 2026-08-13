package com.arka.factory;

import com.arka.company.CompanyEntity;
import com.arka.enums.AddressType;
import com.arka.information.address.AddressEntity;
import com.arka.information.contact.ContactEntity;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.Instant;
import java.util.ArrayList;

public class ContactTestDataFactory {

    private final TestEntityManager entityManager;
    private final CompanyTestDataFactory companyFactory;

    public ContactTestDataFactory(TestEntityManager entityManager) {
        this.entityManager = entityManager;
        this.companyFactory = new CompanyTestDataFactory(entityManager);
    }

    public ContactEntity createContact() {
        return createContact(companyFactory.createCompany());
    }

    public ContactEntity createContact(CompanyEntity company) {
        ContactEntity contact = ContactEntity.builder()
                .name("John")
                .lastName("Doe")
                .companyPosition("Warehouse Manager")
                .email("john.doe." + System.nanoTime() + "@arka.com")
                .active(true)
                .company(company)
                .createdAt(Instant.now())
                .build();

        return entityManager.persistAndFlush(contact);
    }

    /**
     * Creates a contact and attaches a persisted address to it.
     */
    public AddressEntity createAddress(ContactEntity contact, AddressType addressType) {
        AddressEntity address = AddressEntity.builder()
                .country("Colombia")
                .city("Medellin")
                .zipCode("050001")
                .address("Calle 10 #40-20")
                .type(addressType)
                .active(true)
                .build();

        if (contact.getAddresses() == null) {
            contact.setAddresses(new ArrayList<>());
        }
        contact.getAddresses().add(address);

        return entityManager.persistAndFlush(address);
    }

    public AddressEntity createAddress(ContactEntity contact) {
        return createAddress(contact, AddressType.WAREHOUSE);
    }}
