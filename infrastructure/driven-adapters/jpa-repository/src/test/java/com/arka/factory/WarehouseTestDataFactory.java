package com.arka.factory;

import com.arka.enums.AddressType;
import com.arka.information.address.AddressEntity;
import com.arka.information.contact.ContactEntity;
import com.arka.warehouse.WarehouseEntity;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.Instant;

public class WarehouseTestDataFactory {

    private final TestEntityManager entityManager;
    private final ContactTestDataFactory contactFactory;

    public WarehouseTestDataFactory(TestEntityManager entityManager) {
        this.entityManager = entityManager;
        this.contactFactory = new ContactTestDataFactory(entityManager);
    }

    public WarehouseEntity createWarehouse() {
        // 1. Contact factory creates the contact
        ContactEntity contact = contactFactory.createContact();

        // 2. Contact factory creates and links the address to that contact
        AddressEntity address = contactFactory.createAddress(contact, AddressType.WAREHOUSE);

        // 3. Warehouse simply links to the persisted address
        return createWarehouse(address);
    }

    public WarehouseEntity createWarehouse(AddressEntity address) {
        WarehouseEntity warehouse = WarehouseEntity.builder()
                .address(address)
                .createdAt(Instant.now())
                .active(true)
                .build();

        return entityManager.persistAndFlush(warehouse);
    }}
