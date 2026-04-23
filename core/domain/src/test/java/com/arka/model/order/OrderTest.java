package com.arka.model.order;

import com.arka.enums.OrderStatus;
import com.arka.enums.OrderType;
import com.arka.exceptions.InvalidOrderStatusException;
import com.arka.exceptions.InvalidOrderTransitionStatusException;
import com.arka.model.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private Order order;
    private OrderItem item1;
    private OrderItem item2;

    @BeforeEach
    void setUp() {

        //GIVEN
        order = Order.create("ORD-001", "notes", OrderType.SALES);

        Product product1 = Product.builder().id(1L).build();
        Product product2 = Product.builder().id(2L).build();

        item1 = OrderItem.builder()
                .product(product1)
                .quantity(2)
                .unitPrice(BigDecimal.valueOf(10.00))
                .build();

        item2 = OrderItem.builder()
                .product(product2)
                .quantity(3)
                .unitPrice(BigDecimal.valueOf(20.00))
                .build();
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