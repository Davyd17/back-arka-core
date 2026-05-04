package com.arka.usecase;

import com.arka.dto.in.UpdateOrderIn;
import com.arka.dto.in.UpdateOrderItemIn;
import com.arka.enums.CompanyRelationType;
import com.arka.enums.OrderType;
import com.arka.gateway.repository.order.OrderGateway;
import com.arka.model.Company;
import com.arka.model.order.Order;
import com.arka.model.order.OrderItem;
import com.arka.model.product.Product;
import com.arka.model.product.ProductCategory;
import com.arka.service.OrderItemService;
import com.arka.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ModifyOrderUseCaseTest {

    @Mock
    private OrderService orderService;

    @Mock
    private OrderItemService itemService;

    @Mock
    private OrderGateway orderGateway;

    @InjectMocks
    private ModifyOrderUseCase useCase;

    private Order order;

    @BeforeEach
    void setUp() {

        order = Order.create("ORD-001", "old notes", OrderType.SALES, this.buildCompany(1L));
    }

    private Company buildCompany(long id){
        return new Company(
                id,
                "Test Company " + id,
                CompanyRelationType.CUSTOMER,
                new ArrayList<>(),
                new HashSet<>()
        );
    }

    private Product buildProduct(long id, BigDecimal basePrice){
        return new Product(
                id,
                "PT-00" + id,
                "test product-" + id,
                null,
                basePrice,
                new HashMap<>(),
                ProductCategory.create("Test category"),
                true
        );
    }

    // --- input validation ---

    @Test
    void shouldThrowWhenInputIsNull() {

        assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(null));

    }

    // --- Notes ---

    @Test
    void shouldUpdateNotesWhenNotBlank() {

        //Arrange
        UpdateOrderIn input = new UpdateOrderIn(1L, "new notes", Set.of());

        when(orderService.findById(1L)).thenReturn(order);
        when(orderGateway.update(order)).thenReturn(order);

        //Act
        useCase.execute(input);

        //Assert
        assertEquals("new notes", order.getNotes());
    }

    @Test
    void shouldNotUpdateNotesWhenBlank(){

        //Arrange
        UpdateOrderIn input = new UpdateOrderIn(1L, " ", Set.of());

        when(orderService.findById(1L)).thenReturn(order);
        when(orderGateway.update(order)).thenReturn(order);

        //Act
        useCase.execute(input);

        //Assert
        assertEquals("old notes", order.getNotes());
    }

    @Test
    void shouldAddNewItemWhenNotPresentInOrder(){

        //Arrange
        Product product = this.buildProduct(2L, BigDecimal.valueOf(20.00));

        OrderItem incomingItem = OrderItem.create(product, 10);

        Set<UpdateOrderIn.Item> itemsInput =
                Set.of(new UpdateOrderIn.Item(null, 1L, 3));

        UpdateOrderIn orderInput = new UpdateOrderIn(1L, "", itemsInput);

        when(orderService.findById(1L)).thenReturn(order);

        when(itemService.resolveItem(any(), any(), any()))
                .thenReturn(incomingItem);

        when(orderGateway.update(order)).thenReturn(order);

        //Act
        useCase.execute(orderInput);

        //Asser
        assertEquals(1, order.getItems().size());
        assertEquals(BigDecimal.valueOf(10.00), order.getItems().getFirst().getUnitPriceSnapshot());
    }

    @Test
    void shouldRemoveItemNotPresentInIncomingList(){

        //Arrange
        Product product1 = this.buildProduct(1L, BigDecimal.valueOf(10.00));
        Product product2 = this.buildProduct(2L, BigDecimal.valueOf(20.00));

        OrderItem currentItem1 = OrderItem.create(product1, 3);

        OrderItem currentItem2 = OrderItem.create(product2, 5);

        order.addItem(currentItem1);
        order.addItem(currentItem2);

        OrderItem incomingItem =
                OrderItem.create(product1, 3);

        UpdateOrderIn orderInput =
                new UpdateOrderIn(1L, "", Set.of(
                        new UpdateOrderItemIn(null, 1L, 3)));

        when(orderService.findById(1L)).thenReturn(order);

        when(itemService.resolveItem(any(), any(), any()))
                .thenReturn(incomingItem);

        when(orderGateway.update(order)).thenReturn(order);

        //Act
        useCase.execute(orderInput);

        //AsserThat
        assertEquals(1, order.getItems().size());
        assertEquals(product1.getId(),
                order.getItems().getFirst().getProduct().getId());


    }

    @Test
    void shouldUpdateQuantityWhenItemAlreadyExists() {

        //Arrange
        Product product1 = this.buildProduct(1L, BigDecimal.valueOf(10.00));
        Product product2 = this.buildProduct(2L, BigDecimal.valueOf(20.00));


        OrderItem currentItem1 = OrderItem.create(product1, 3);

        OrderItem currentItem2 = OrderItem.create(product2, 5);

        order.addItem(currentItem1);
        order.addItem(currentItem2);

        OrderItem incomingItem1 = OrderItem.create(product1, 3);

        //Update quantity of item2 from 5 to 8
        OrderItem incomingItem2 = OrderItem.create(product2, 8);

        UpdateOrderIn orderInput;
        orderInput = new UpdateOrderIn(1L, "", Set.of(
                new UpdateOrderIn.Item(null, 1L, 3),
                new UpdateOrderIn.Item(null, 2L, 8)));

        when(orderService.findById(1L)).thenReturn(order);

        when(itemService.resolveItem(any(), any(), any()))
                .thenReturn(incomingItem2);

        when(orderGateway.update(order)).thenReturn(order);

        //Act
        useCase.execute(orderInput);

        //AsserThat
        assertEquals(2, order.getItems().size());
        assertEquals(8, order.getItems().get(1).getQuantity());
    }

    @Test
    void shouldAlwaysCallGatewayUpdate() {
        UpdateOrderIn input = new UpdateOrderIn(1L, "", Set.of());

        when(orderService.findById(1L)).thenReturn(order);
        when(orderGateway.update(order)).thenReturn(order);

        useCase.execute(input);

        verify(orderGateway).update(order);
    }
}