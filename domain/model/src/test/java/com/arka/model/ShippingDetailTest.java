package com.arka.model;

import com.arka.enums.AddressType;
import com.arka.enums.OrderStatus;
import com.arka.enums.OrderType;
import com.arka.enums.ShippingStatus;
import com.arka.exceptions.DuplicationException;
import com.arka.exceptions.InvalidTransitionStatusException;
import com.arka.model.information.Address;
import com.arka.model.order.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ShippingDetailTest {

    private ShippingDetail shippingDetail;
    private Order order;
    private Address origin;
    private Address destination;

    @BeforeEach
    void setUp(){

        shippingDetail = ShippingDetail.create(
                "Test carrier",
                "Test-98799",
                buildOrder(1L),
                null,
                buildAddress(1L),
                buildAddress(2L));

    }

    private Order buildOrder(Long id){
        return new Order(id,
                "O-00" + id,
                OrderStatus.PENDING,
                "Notes test",
                OrderType.SALES,
                BigDecimal.valueOf(100.00),
                null,
                new ArrayList<>(),
                Instant.now(),
                null);
    }

    private Address buildAddress(Long id){
        return new Address(id,
                "country",
                "city",
                null,
                "test street #10",
                "Test notes",
                AddressType.CUSTOMER,
                true);
    }

    //-- create --

    @Test
    void shouldCreateShippingDetailWithPendingStatus(){
        shippingDetail = ShippingDetail.create(
                "Test carrier",
                "Test-98799",
                buildOrder(1L),
                null,
                buildAddress(1L),
                buildAddress(2L));

        assertThat(shippingDetail.getStatus())
                .isEqualByComparingTo(ShippingStatus.PENDING);
    }

    @Test
    void shouldThrowWhenReceiveAddressDuplication(){

        assertThrows(DuplicationException.class, () ->
                ShippingDetail.create(
                        "Test carrier",
                        "Test-98799",
                        buildOrder(1L),
                        null,
                        buildAddress(1L),
                        buildAddress(1L)));
    }

// --- status transitions ---

    @Test
    void shouldTransitionFromPendingToInDispatch() {
        shippingDetail.updateStatus(ShippingStatus.IN_DISPATCH);
        assertEquals(ShippingStatus.IN_DISPATCH, shippingDetail.getStatus());
    }

    @Test
    void shouldThrowWhenInvalidTransitionFromPending() {
        assertThrows(InvalidTransitionStatusException.class,
                () -> shippingDetail.updateStatus(ShippingStatus.SENT));
    }

    @Test
    void shouldTransitionFromInDispatchToSent() {
        shippingDetail.updateStatus(ShippingStatus.IN_DISPATCH);
        shippingDetail.updateStatus(ShippingStatus.SENT);
        assertEquals(ShippingStatus.SENT, shippingDetail.getStatus());
    }

    @Test
    void shouldThrowWhenInvalidTransitionFromInDispatch() {
        shippingDetail.updateStatus(ShippingStatus.IN_DISPATCH);
        assertThrows(InvalidTransitionStatusException.class,
                () -> shippingDetail.updateStatus(ShippingStatus.PENDING));
    }

    @Test
    void shouldTransitionFromSentToDelivered() {
        shippingDetail.updateStatus(ShippingStatus.IN_DISPATCH);
        shippingDetail.updateStatus(ShippingStatus.SENT);
        shippingDetail.updateStatus(ShippingStatus.DELIVERED);
        assertEquals(ShippingStatus.DELIVERED, shippingDetail.getStatus());
    }

    @Test
    void shouldThrowWhenInvalidTransitionFromDelivered() {
        shippingDetail.updateStatus(ShippingStatus.IN_DISPATCH);
        shippingDetail.updateStatus(ShippingStatus.SENT);
        shippingDetail.updateStatus(ShippingStatus.DELIVERED);
        assertThrows(InvalidTransitionStatusException.class,
                () -> shippingDetail.updateStatus(ShippingStatus.PENDING));
    }

    @Test
    void shouldTransitionFromDeliveredToReceived() {
        shippingDetail.updateStatus(ShippingStatus.IN_DISPATCH);
        shippingDetail.updateStatus(ShippingStatus.SENT);
        shippingDetail.updateStatus(ShippingStatus.DELIVERED);
        shippingDetail.updateStatus(ShippingStatus.RECEIVED);
        assertEquals(ShippingStatus.RECEIVED, shippingDetail.getStatus());
    }

    @Test
    void shouldThrowWhenInvalidTransitionFromReceived() {
        shippingDetail.updateStatus(ShippingStatus.IN_DISPATCH);
        shippingDetail.updateStatus(ShippingStatus.SENT);
        shippingDetail.updateStatus(ShippingStatus.DELIVERED);
        shippingDetail.updateStatus(ShippingStatus.RECEIVED);
        assertThrows(InvalidTransitionStatusException.class,
                () -> shippingDetail.updateStatus(ShippingStatus.PENDING));
    }

}