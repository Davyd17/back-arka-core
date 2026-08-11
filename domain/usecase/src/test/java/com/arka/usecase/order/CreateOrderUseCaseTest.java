package com.arka.usecase.order;

import com.arka.entities.information.Contact;
import com.arka.entities.product.Product;
import com.arka.entities.product.ProductCategory;
import com.arka.order.CreateOrderUseCase;
import com.arka.order.dto.CreateOrderIn;
import com.arka.enums.OrderType;
import com.arka.order.gateway.OrderGateway;
import com.arka.party.service.ContactService;
import com.arka.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateOrderUseCaseTest {

    @Mock
    private OrderGateway orderGateway;

    @Mock
    private ContactService contactService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private CreateOrderUseCase useCase;

    private Contact contact;

    private final String OWNER_EMAIL = "jhon.conor@example.com";

    @BeforeEach
    void setUp() {
        contact = new Contact(
                1L,
                "Jhon",
                "Conor",
                "Test Position",
                null,
                OWNER_EMAIL,
                new ArrayList<>(),
                new ArrayList<>(),
                true);
    }

    private Product buildProduct(long id, BigDecimal basePrice) {
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
                () -> useCase.execute(null, null));
    }

    @Test
    void shouldCreateOrderWithInputItems() {
        //Arrange
        Product product = buildProduct(1L, BigDecimal.valueOf(10.00));

        CreateOrderIn input = new CreateOrderIn(
                null,
                OrderType.SALES,
                List.of(new CreateOrderIn.Item(1L, 10)));

        when(contactService.findByEmail(OWNER_EMAIL)).thenReturn(contact);
        when(productService.findById(1L)).thenReturn(product);

        when(orderGateway.save(any()))
                .thenAnswer(i -> i.getArgument(0));

        //Act
        useCase.execute(input, OWNER_EMAIL);

        //Assert
        verify(orderGateway).save(argThat(order ->
                order.getItems().size() == 1 &&
                        order.getItems().getFirst().getQuantity() == 10));
    }

    @Test
    void shouldAlwaysCallGatewaySave() {

        //Arrange
        Product product = buildProduct(1L, BigDecimal.valueOf(10.00));

        CreateOrderIn input = new CreateOrderIn(
                null,
                OrderType.SALES,
                List.of(new CreateOrderIn.Item(1L, 10)));

        when(contactService.findByEmail(OWNER_EMAIL)).thenReturn(contact);
        when(productService.findById(1L)).thenReturn(product);
        when(orderGateway.save(any()))
                .thenAnswer(i -> i.getArgument(0));

        //Act
        useCase.execute(input, OWNER_EMAIL);

        //Assert
        verify(orderGateway).save(any());

    }
}
