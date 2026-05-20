package com.arka.model.cart;

import com.arka.model.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartItemTest {

    private Product product;
    private ShoppingCartItem item;

    @BeforeEach
    void setUp() {

    }

    private Product buildProduct(Long id, BigDecimal price, boolean active) {
        return new Product(id, "SKU-00" + id, "Product " + id, null,
                price, new HashMap<>(), null, active);
    }

    // --- create ----

    @Test
    void shouldCreateItemWithUnitPriceFromProductBasePrice(){

        product = buildProduct(1L, BigDecimal.valueOf(10.00), true);
        item = ShoppingCartItem.create(1, product);

        assertThat(item.getUnitPrice()).isEqualByComparingTo(BigDecimal.valueOf(10.00));
    }

    @Test
    void shouldCreateItemWithCorrectSubtotal(){

        product = buildProduct(1L, BigDecimal.valueOf(10.00), true);
        item = ShoppingCartItem.create(2, product);

        assertThat(item.getSubTotal()).isEqualByComparingTo(BigDecimal.valueOf(20.00));
    }

    @Test
    void shouldThrowWhenQuantityIsZeroOrNegative(){

        product = buildProduct(1L, BigDecimal.valueOf(10.00), true);

        assertThrows(IllegalArgumentException.class, () ->
                ShoppingCartItem.create(0, product));

        assertThrows(IllegalArgumentException.class, () ->
                ShoppingCartItem.create(-3, product));
    }

    // -- updateQuantity --
    @Test
    void shouldUpdateSubtotalWhenUpdateQuantity(){

        product = buildProduct(1L, BigDecimal.valueOf(10.00), true);

        item = ShoppingCartItem.create(1,  product);
        item.updateQuantity(2);

        assertEquals(2, item.getQuantity());
        assertThat(item.getSubTotal()).isEqualByComparingTo(BigDecimal.valueOf(20.00));
    }
}