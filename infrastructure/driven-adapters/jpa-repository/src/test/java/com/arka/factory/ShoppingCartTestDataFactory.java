package com.arka.factory;

import com.arka.cart.ShoppingCartEntity;
import com.arka.enums.ShoppingCartStatus;
import com.arka.information.contact.ContactEntity;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.Instant;

public class ShoppingCartTestDataFactory {

    private final TestEntityManager entityManager;

    public ShoppingCartTestDataFactory(TestEntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public ShoppingCartEntity createShoppingCart(ContactEntity contact) {
        ShoppingCartEntity cart = ShoppingCartEntity.builder()
                .status(ShoppingCartStatus.ACTIVE)
                .totalAmount(BigDecimal.ZERO)
                .contact(contact)
                .createdAt(Instant.now())
                .build();

        return entityManager.persistAndFlush(cart);
    }
}
