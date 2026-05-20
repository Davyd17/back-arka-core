package com.arka.model.cart;

import com.arka.enums.ShoppingCartStatus;
import com.arka.exceptions.InvalidEditableStatusException;
import com.arka.model.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTest {

    private ShoppingCart cart;
    private Product product;

    @BeforeEach
    void setUp() {
        product = buildProduct(1L, BigDecimal.valueOf(10.00), true);

        cart = ShoppingCart.create(1L);
    }

    private Product buildProduct(Long id, BigDecimal price, boolean active) {
        return new Product(id, "SKU-00" + id, "Product " + id, null,
                price, new HashMap<>(), null, active);
    }

    private ShoppingCart buildCart(ShoppingCartStatus status,
                                   Instant createdAt,
                                   Instant updatedAt) {
        return new ShoppingCart(null, status, BigDecimal.ZERO,
                new ArrayList<>(), createdAt, updatedAt, 1L);
    }

    // --- create ---

    @Test
    void shouldCreateCartAsActiveByDefault() {
        assertEquals(ShoppingCartStatus.ACTIVE, cart.getStatus());
    }

    @Test
    void shouldInitializeTotalAmountAsZero() {
        assertThat(cart.getTotalAmount()).isEqualByComparingTo(BigDecimal.ZERO);
    }

    // --- addItem ---

    @Test
    void shouldAddNewItemAndUpdateTotalAmount() {
        cart.addItem(product, 2);

        assertEquals(1, cart.getItems().size());
        assertThat(cart.getTotalAmount()).isEqualByComparingTo(BigDecimal.valueOf(20.00));
    }

    @Test
    void shouldUpdateQuantityWhenItemAlreadyExists() {
        cart.addItem(product, 2);
        cart.addItem(product, 5);

        assertEquals(1, cart.getItems().size());
        assertEquals(5, cart.getItems().getFirst().getQuantity());
    }

    @Test
    void shouldThrowWhenProductIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> cart.addItem(null, 1));
    }

    @Test
    void shouldThrowWhenProductIsInactive() {
        Product inactive = buildProduct(2L, BigDecimal.valueOf(20.00), false);

        assertThrows(IllegalStateException.class,
                () -> cart.addItem(inactive, 1));
    }

    @Test
    void shouldThrowWhenQuantityIsZeroOrNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> cart.addItem(product, 0));

        assertThrows(IllegalArgumentException.class,
                () -> cart.addItem(product, -1));
    }

    @Test
    void shouldThrowWhenAddingItemToProcessedCart() {
        cart.cancel();

        assertThrows(InvalidEditableStatusException.class,
                () -> cart.addItem(product, 1));
    }

    @Test
    void shouldReactivateAbandonedCartWhenAddingItem() {
        cart.abandon();
        cart.addItem(product, 1);

        assertEquals(ShoppingCartStatus.ACTIVE, cart.getStatus());
    }

    // --- abandon / cancel ---

    @Test
    void shouldAbandonActiveCart() {
        cart.abandon();
        assertEquals(ShoppingCartStatus.ABANDONED, cart.getStatus());
    }

    @Test
    void shouldCancelActiveCart() {
        cart.cancel();
        assertEquals(ShoppingCartStatus.CANCELLED, cart.getStatus());
    }

    // --- isExpired / isAbandoned ---

    @Test
    void shouldReturnTrueWhenCartIsExpired() {
        ShoppingCart expiredCart = buildCart(
                ShoppingCartStatus.ACTIVE,
                Instant.now().minus(Duration.ofDays(31)),
                null);

        assertTrue(expiredCart.isExpired(Duration.ofDays(30)));
    }

    @Test
    void shouldReturnFalseWhenCartIsNotExpired() {
        ShoppingCart freshCart = buildCart(
                ShoppingCartStatus.ACTIVE,
                Instant.now(),
                null);

        assertFalse(freshCart.isExpired(Duration.ofDays(30)));
    }

    @Test
    void shouldReturnTrueWhenCartIsAbandonedByInactivity() {
        ShoppingCart inactiveCart = buildCart(
                ShoppingCartStatus.ACTIVE,
                Instant.now().minus(Duration.ofDays(10)),
                Instant.now().minus(Duration.ofDays(8)));

        assertTrue(inactiveCart.isAbandoned(Duration.ofDays(7)));
    }

    @Test
    void shouldUseCreatedAtWhenUpdatedAtIsNull() {
        ShoppingCart cartWithoutUpdate = buildCart(
                ShoppingCartStatus.ACTIVE,
                Instant.now().minus(Duration.ofDays(8)),
                null);

        assertTrue(cartWithoutUpdate.isAbandoned(Duration.ofDays(7)));
    }
}