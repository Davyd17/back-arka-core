package com.arka.usecase.shipping;

import com.arka.dto.in.CreateShippingDetailIn;
import com.arka.enums.AddressType;
import com.arka.enums.OrderStatus;
import com.arka.enums.OrderType;
import com.arka.exceptions.DuplicationException;
import com.arka.gateway.ShippingDetailGateway;
import com.arka.mapper.ShippingDetailMapper;
import com.arka.mapper.ShippingDetailMapperImpl;
import com.arka.model.ShippingDetail;
import com.arka.model.information.Address;
import com.arka.model.order.Order;
import com.arka.service.AddressService;
import com.arka.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterShippingDetailsUseCaseTest {

    @Mock
    private ShippingDetailGateway gateway;

    @Mock
    private OrderService orderService;

    @Mock
    private AddressService addressService;

    @Mock
    private ShippingDetailMapper shippingDetailMapper;

    @InjectMocks
    private  RegisterShippingDetailsUseCase useCase;

    private Order order;


    @BeforeEach
    void setUp(){

        order = new Order(
                1L, "OR-001", OrderStatus.PENDING, null,
                OrderType.SALES, BigDecimal.valueOf(100.00), null,
                new ArrayList<>(), Instant.now(), null);

    }

    private Address buildAddress(Long id){
        return new Address(
                id,
                "Country",
                "City",
                "ZipTest",
                String.format("00%d test street", id),
                null,
                AddressType.CUSTOMER,
                true);
    }

    private CreateShippingDetailIn buildInput(Long orderId, Long originId, Long destinationId){
        return new CreateShippingDetailIn(
                "Coordinadora",
                "01-200",
                null,
                orderId,
                originId,
                destinationId
        );
    }

    // -- Null Validation --

    @Test
    void shouldThrowWhenInputIsNull(){
        assertThrows(IllegalArgumentException.class, () ->
                useCase.execute(null));
    }

    @Test
    void shouldCreateShippingDetailWithCorrectOrderAndAddresses(){

        Address origin = buildAddress(1L);
        Address destination = buildAddress(2L);

        CreateShippingDetailIn input =
                buildInput(1L, 1L, 2L);


        when(orderService.findById(input.orderId())).thenReturn(order);
        when(addressService.findById(input.originAddressId())).thenReturn(origin);
        when(addressService.findById(input.destinationAddressId()))
                .thenReturn(destination);

        when(gateway.save(any())).thenAnswer(
                i -> i.getArgument(0));

        useCase.execute(input);

        verify(gateway).save(argThat(shDet ->
                shDet.getOrder().getNumber().equals("OR-001") &&
                shDet.getOrigin().getId() == 1L &&
                shDet.getDestination().getId() == 2L));

    }

    @Test
    void shouldThrowWhenOriginAndDestinationAddressAreTheSame(){

        Address origin = buildAddress(1L);
        Address destination = buildAddress(1L);

        CreateShippingDetailIn input =
                buildInput(1L, 1L, 1L);


        when(orderService.findById(input.orderId())).thenReturn(order);
        when(addressService.findById(input.originAddressId())).thenReturn(origin);
        when(addressService.findById(input.destinationAddressId()))
                .thenReturn(destination);

        assertThrows(DuplicationException.class, () ->
                useCase.execute(input));
    }

    @Test
    void shouldAlwaysCallGatewaySave(){
        Address origin = buildAddress(1L);
        Address destination = buildAddress(2L);

        CreateShippingDetailIn input =
                buildInput(1L, 1L, 2L);


        when(orderService.findById(input.orderId())).thenReturn(order);
        when(addressService.findById(input.originAddressId())).thenReturn(origin);
        when(addressService.findById(input.destinationAddressId()))
                .thenReturn(destination);

        useCase.execute(input);

        verify(gateway).save(any());
    }
}