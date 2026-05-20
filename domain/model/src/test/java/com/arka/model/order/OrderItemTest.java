package com.arka.model.order;

import com.arka.model.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemTest {

    private OrderItem item;

    @BeforeEach
    void setUp(){

        //Arrange
        item = OrderItem.create(
                buildProduct(1L, BigDecimal.valueOf(10.00)),
                5);

    }

    private Product buildProduct(Long id, BigDecimal basePrice){

        return new Product(
                id,
                "SKU-00" + id,
                "Test Product" + id,
                null,
                basePrice,
                new HashMap<>(),
                null,
                true
        );
    }

    @Test
    void shouldCalculateTotalPriceWhenCreating(){
        assertEquals(BigDecimal.valueOf(50.00), item.getTotalPrice());
    }

    @Test
    void shouldRecalculateTotalPriceWhenUpdateQuantity(){

        //Act
        this.item.updateQuantity(8);

        //Assert
        assertEquals(BigDecimal.valueOf(80.00), this.item.getTotalPrice());

    }

    @Test
    void shouldThrowWhenCreateItemWithZeroQuantity(){
        assertThrows(IllegalArgumentException.class, () -> OrderItem.create(
                buildProduct(2L, BigDecimal.valueOf(10.00)),
                0));

    }

    @Test
    void shouldThrowWhenCreateItemWithNegativeQuantity(){
        assertThrows(IllegalArgumentException.class, () -> OrderItem.create(
                buildProduct(2L, BigDecimal.valueOf(10.00)),
                -5));

    }

    @Test
    void shouldThrowWhenUpdateQuantityToZero(){
        assertThrows(IllegalArgumentException.class,
                () -> this.item.updateQuantity(0));
    }

    @Test
    void shouldThrowWhenUpdateQuantityToNegative(){
        assertThrows(IllegalArgumentException.class,
                () -> this.item.updateQuantity(-1));
    }

}