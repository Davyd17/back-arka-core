package com.arka.factory;

import com.arka.enums.OrderStatus;
import com.arka.enums.OrderType;
import com.arka.information.contact.ContactEntity;
import com.arka.order.OrderEntity;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.Instant;

public class OrderTestDataFactory {

    private final TestEntityManager entityManager;

    public OrderTestDataFactory(TestEntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public OrderEntity createOrder(ContactEntity contact, Instant createdAt) {
        OrderEntity order = OrderEntity.builder()
                .number("ORD-" + System.currentTimeMillis())
                .status(OrderStatus.PENDING)
                .type(OrderType.PURCHASE)
                .totalPrice(new BigDecimal("99.99"))
                .contact(contact)
                .createdAt(createdAt)
                .build();

        return entityManager.persistAndFlush(order);
    }
}
