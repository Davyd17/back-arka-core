package com.arka.model.order;

import com.arka.enums.OrderStatus;
import com.arka.enums.OrderType;
import com.arka.exceptions.InvalidOrderStatusException;
import com.arka.exceptions.InvalidOrderTransitionStatusException;
import com.arka.model.Company;
import com.arka.model.product.Product;
import com.arka.model.product.ProductCategory;
import jdk.jfr.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private Order order;
    private OrderItem item1;
    private OrderItem item2;

    @BeforeEach
    void setUp() {

        //GIVEN

        order = Order.create("ORD-001", "notes", OrderType.SALES, null);

        item1 = OrderItem.create(
                buildProduct(1L, BigDecimal.valueOf(10.00)),
                2);

        item2 = OrderItem.create(
                buildProduct(2L, BigDecimal.valueOf(20.00)),
                3);
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

    // --- updateTotalPrice ---

    @Test
    void shouldCalculateTotalPriceCorrectlyAfterAddingItems() {

        //WHEN
        order.addItem(item1); // 2 * 10 = 20
        order.addItem(item2); // 3 * 20 = 60

        //THEN
        assertEquals(BigDecimal.valueOf(80.00), order.getTotalPrice());
    }

    @Test
    void shouldRecalculateTotalPriceAfterRemovingItem() {
        order.addItem(item1);
        order.addItem(item2);
        order.removeItem(1L);

        assertEquals(BigDecimal.valueOf(60.00), order.getTotalPrice());
    }

    @Test
    void shouldRecalculateTotalPriceAfterChangingQuantity() {
        order.addItem(item1); // 2 * 10 = 20
        order.changeItemQuantity(1L, 5); // 5 * 10 = 50

        assertEquals(BigDecimal.valueOf(50.00), order.getTotalPrice());
    }

    @Test
    void shouldReturnZeroWhenNoItems() {

        order.removeItem(1L);
        order.removeItem(2L);

        assertEquals(BigDecimal.ZERO, order.getTotalPrice());
    }

    // --- updateStatus / transitionTo ---

    @Test
    void shouldTransitionFromPendingToProcessing() {

        order.updateStatus(OrderStatus.PROCESSING);

        assertEquals(OrderStatus.PROCESSING, order.getStatus());
    }

    @Test
    void shouldTransitionFromProcessingToCancelled() {

        order.updateStatus(OrderStatus.PROCESSING);
        order.updateStatus(OrderStatus.CANCELLED);

        assertEquals(OrderStatus.CANCELLED, order.getStatus());
    }

    @Test
    void shouldTransitionFromProcessingToAuthorized() {

        order.updateStatus(OrderStatus.PROCESSING);
        order.updateStatus(OrderStatus.AUTHORIZED);

        assertEquals(OrderStatus.AUTHORIZED, order.getStatus());
    }

    @Test
    void shouldThrowWhenTransitioningFromAuthorizedToAnyStatus() {

        order.updateStatus(OrderStatus.PROCESSING);
        order.updateStatus(OrderStatus.AUTHORIZED);

        assertThrows(InvalidOrderTransitionStatusException.class,
                () -> order.updateStatus(OrderStatus.PENDING));
    }

    @Test
    void shouldThrowWhenTransitioningFromCancelledToAnyStatus() {

        order.updateStatus(OrderStatus.PROCESSING);
        order.updateStatus(OrderStatus.CANCELLED);

        assertThrows(InvalidOrderTransitionStatusException.class,
                () -> order.updateStatus(OrderStatus.PROCESSING));
    }

    @Test
    void shouldThrowWhenTransitioningFromAuthorizedToPending() {

        order.updateStatus(OrderStatus.PROCESSING);
        order.updateStatus(OrderStatus.AUTHORIZED);

        assertThrows(InvalidOrderTransitionStatusException.class,
                () -> order.updateStatus(OrderStatus.PENDING));
    }

    // --- validateEditionStatus ---

    @Test
    void shouldThrowWhenAddingItemToAuthorizedOrder() {

        order.updateStatus(OrderStatus.PROCESSING);
        order.updateStatus(OrderStatus.AUTHORIZED);

        assertThrows(InvalidOrderStatusException.class,
                () -> order.addItem(item1));
    }

    @Test
    void shouldThrowWhenRemovingItemFromCancelledOrder() {

        order.updateStatus(OrderStatus.PROCESSING);
        order.updateStatus(OrderStatus.CANCELLED);

        assertThrows(InvalidOrderStatusException.class,
                () -> order.removeItem(1L));
    }

    @Test
    void shouldThrowWhenUpdatingNotesOnAuthorizedOrder() {

        order.updateStatus(OrderStatus.PROCESSING);
        order.updateStatus(OrderStatus.AUTHORIZED);

        assertThrows(InvalidOrderStatusException.class,
                () -> order.updateNotes("new notes"));
    }
}