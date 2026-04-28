package com.arka.model.product;

import com.arka.exceptions.InvalidProductStateException;
import com.arka.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    private Product product;

    @BeforeEach
    void setUp() {

        product = Product.create(
                "SKU-001",
                "Test Product",
                "Test Description",
                BigDecimal.valueOf(10.00),
                null
        );
    }

    // --- create ---

    @Test
    void shouldCreateProductAsActiveByDefault() {
        assertTrue(product.isActive());
    }

    @Test
    void shouldCreateProductWithNullDescription() {
        Product product = Product.create(
                "SKU-002",
                "Test Product",
                null,
                BigDecimal.valueOf(10.00),
                null
        );

        assertNull(product.getDescription());
    }

    @Test
    void shouldInitializeAttributesAsEmptyMap() {
        assertNotNull(product.getAttributes());
        assertTrue(product.getAttributes().isEmpty());
    }

    // --- activate / deactivate ---

    @Test
    void shouldDeactivateActiveProduct() {
        product.deactivate();

        assertFalse(product.isActive());
    }

    @Test
    void shouldActivateInactiveProduct() {
        product.deactivate();
        product.activate();

        assertTrue(product.isActive());
    }

    @Test
    void shouldThrowWhenActivatingAlreadyActiveProduct() {
        assertThrows(InvalidProductStateException.class,
                () -> product.activate());
    }

    @Test
    void shouldThrowWhenDeactivatingAlreadyInactiveProduct() {
        product.deactivate();

        assertThrows(InvalidProductStateException.class,
                () -> product.deactivate());
    }

    // --- updatePrice ---

    @Test
    void shouldUpdatePriceWhenValid() {
        product.updatePrice(BigDecimal.valueOf(20.00));

        assertThat(product.getBasePrice())
                .isEqualByComparingTo(BigDecimal.valueOf(20.00));
    }

    @Test
    void shouldThrowWhenPriceIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> product.updatePrice(null));
    }

    @Test
    void shouldThrowWhenPriceIsZero() {
        assertThrows(IllegalArgumentException.class,
                () -> product.updatePrice(BigDecimal.ZERO));
    }

    @Test
    void shouldThrowWhenPriceIsNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> product.updatePrice(BigDecimal.valueOf(-5.00)));
    }

    // --- attributes ---

    @Test
    void shouldAddAttribute() {
        product.addAttribute("color", "red");

        assertEquals("red", product.getAttributes().get("color"));
    }

    @Test
    void shouldRemoveAttribute() {
        product.addAttribute("color", "red");
        product.removeAttribute("color");

        assertFalse(product.getAttributes().containsKey("color"));
    }

    @Test
    void shouldUpdateAttribute() {
        product.addAttribute("color", "red");
        product.updateAttribute("color", "blue");

        assertEquals("blue", product.getAttributes().get("color"));
    }

    @Test
    void shouldThrowWhenRemovingNonExistentAttribute() {
        assertThrows(NotFoundException.class,
                () -> product.removeAttribute("nonexistent"));
    }

    @Test
    void shouldThrowWhenUpdatingNonExistentAttribute() {
        assertThrows(NotFoundException.class,
                () -> product.updateAttribute("nonexistent", "value"));
    }
}