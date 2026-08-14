package com.arka.usecase.order;

import com.arka.entities.information.Contact;
import com.arka.entities.order.Order;
import com.arka.entities.order.OrderItem;
import com.arka.entities.product.Product;
import com.arka.entities.product.ProductCategory;
import com.arka.exceptions.UnauthorizedException;
import com.arka.order.ModifyOrderUseCase;
import com.arka.order.dto.UpdateOrderIn;
import com.arka.enums.OrderType;
import com.arka.order.gateway.OrderGateway;
import com.arka.order.service.OrderItemService;
import com.arka.order.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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

    private final String EMAIL_ORDER_OWNER = "jhon.conor@example.com";

    @BeforeEach
    void setUp() {

        order = Order.create("old notes", OrderType.SALES, buildContact(1L));
    }

    private Contact buildContact(long id){
        return new Contact(
                id,
                "John",
                "Conor",
                "Test Position",
                null,
                EMAIL_ORDER_OWNER,
                new ArrayList<>(),
                new ArrayList<>(),
                true);
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

    // --- valid ownership
    @Test
    void shouldThrowUnauthorizedWhenCallerIsNotOwner() {
        //given
        UpdateOrderIn input = new UpdateOrderIn("New notes", Set.of());
        String invalidOwner = "not.jhon.conor@example.com";

        when(orderService.findById(1L)).thenReturn(order);

        //when & then
        assertThrows(UnauthorizedException.class,
                () -> useCase.execute(input, 1L, invalidOwner));

        verify(orderGateway, never()).save(any());
    }

    // --- input validation ---

    @Test
    void shouldThrowWhenInputIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(null, null, null));
    }

    @Test
    void shouldThrowWhenCallerNotOwnOrder(){

        String notOwnerEmail = "not.jhon.conor@example.com";

        UpdateOrderIn input = new UpdateOrderIn("new notes", Set.of());

        when(orderService.findById(1L)).thenReturn(order);

        assertThrows(UnauthorizedException.class,
                () -> useCase.execute(input, 1L, notOwnerEmail));
    }

    // --- Notes ---

    @Test
    void shouldUpdateNotesWhenNotBlank() {

        //Arrange
        UpdateOrderIn input = new UpdateOrderIn("new notes", Set.of());

        when(orderService.findById(1L)).thenReturn(order);
        when(orderGateway.save(order)).thenReturn(order);

        //Act
        useCase.execute(input, 1L, EMAIL_ORDER_OWNER);

        //Assert
        assertEquals("new notes", order.getNotes());
    }

    @Test
    void shouldNotUpdateNotesWhenBlank(){

        //Arrange
        UpdateOrderIn input = new UpdateOrderIn(" ", Set.of());

        when(orderService.findById(1L)).thenReturn(order);
        when(orderGateway.save(order)).thenReturn(order);

        //Act
        useCase.execute(input, 1L, EMAIL_ORDER_OWNER);

        //Assert
        assertEquals("old notes", order.getNotes());
    }

    @Test
    void shouldAddNewItemWhenNotPresentInOrder(){

        //Arrange
        Product product1 = this.buildProduct(1L, BigDecimal.valueOf(10.00));
        Product product2 = this.buildProduct(2L, BigDecimal.valueOf(20.00));

        OrderItem existingItem = OrderItem.create(product1, 30);
        order.addItem(existingItem);

        OrderItem incomingItem = OrderItem.create(product2, 10);

        Set<UpdateOrderIn.Item> itemsInput =
                Set.of(new UpdateOrderIn.Item(null, 1L, 3));

        UpdateOrderIn orderInput = new UpdateOrderIn("", itemsInput);

        when(orderService.findById(1L)).thenReturn(order);

        when(itemService.resolveItem(any(OrderType.class), anyLong(), anyInt()))
                .thenReturn(incomingItem);

        when(orderGateway.save(order)).thenReturn(order);

        //Act
        useCase.execute(orderInput, 1L, EMAIL_ORDER_OWNER);

        //Asser
        assertEquals(2, order.getItems().size());
        assertEquals(BigDecimal.valueOf(10.00),
                order.getItems().getFirst().getUnitPriceSnapshot());
        assertEquals(BigDecimal.valueOf(500.00), order.getTotalPrice());
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
                new UpdateOrderIn("", Set.of(
                        new UpdateOrderIn.Item(null, 1L, 3)));

        when(orderService.findById(1L)).thenReturn(order);

        when(itemService.resolveItem(any(OrderType.class), anyLong(), anyInt()))
                .thenReturn(incomingItem);

        when(orderGateway.save(order)).thenReturn(order);

        //Act
        useCase.execute(orderInput, 1L, EMAIL_ORDER_OWNER);

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
        orderInput = new UpdateOrderIn("", Set.of(
                new UpdateOrderIn.Item(null, 1L, 3),
                new UpdateOrderIn.Item(null, 2L, 8)));

        when(orderService.findById(1L)).thenReturn(order);

        when(itemService.resolveItem(any(OrderType.class), anyLong(), anyInt()))
                .thenReturn(incomingItem2);

        when(orderGateway.save(order)).thenReturn(order);

        //Act
        useCase.execute(orderInput, 1L, EMAIL_ORDER_OWNER);

        //AsserThat
        assertEquals(2, order.getItems().size());
        assertEquals(8, order.getItems().get(1).getQuantity());
    }

    @Test
    void shouldAlwaysCallGatewayUpdate() {
        UpdateOrderIn input = new UpdateOrderIn("", Set.of());

        when(orderService.findById(1L)).thenReturn(order);
        when(orderGateway.save(order)).thenReturn(order);

        useCase.execute(input, 1L, EMAIL_ORDER_OWNER);

        verify(orderGateway).save(order);
    }
}
