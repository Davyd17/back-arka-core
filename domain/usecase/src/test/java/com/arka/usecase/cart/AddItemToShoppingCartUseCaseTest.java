package com.arka.usecase.cart;

import com.arka.cart.AddItemToShoppingCartUseCase;
import com.arka.cart.dto.AddItemShoppingCartIn;
import com.arka.enums.ShoppingCartStatus;
import com.arka.gateway.ShoppingCartGateway;
import com.arka.cart.mapper.ShoppingCartMapper;
import com.arka.model.cart.ShoppingCart;
import com.arka.model.product.Product;
import com.arka.model.product.ProductCategory;
import com.arka.product.service.ProductService;
import com.arka.inventory.service.WarehouseInventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddItemToShoppingCartUseCaseTest {

    @Mock
    private ShoppingCartGateway cartGateway;

    @Mock
    private ProductService productService;

    @Mock
    private WarehouseInventoryService inventoryService;

    @Mock
    private ShoppingCartMapper mapper;

    @InjectMocks
    private AddItemToShoppingCartUseCase useCase;

    private Product product;

    @BeforeEach
    void setUp() {
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

    private ShoppingCart buildCart(Long id, ShoppingCartStatus status) {
        return new ShoppingCart(
                id,
                status,
                BigDecimal.ZERO,
                new ArrayList<>(),
                null,
                null,
                1L);
    }

    //--- input validation --

    @Test
    void shouldThrowWhenInputIsNull() {

        assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(null));
    }

    @Test
    void shouldAddNewItemToCartIfCartExistsAndIsActive() {
        Product product = buildProduct(1L, BigDecimal.valueOf(10.00));

        ShoppingCart existingCart =
                buildCart(1L, ShoppingCartStatus.ACTIVE);

        AddItemShoppingCartIn input =
                new AddItemShoppingCartIn(1L, 1L, 10);

        when(productService.findById(input.productId())).thenReturn(product);
        when(cartGateway.getLastCreatedCart(input.userId()))
                .thenReturn(Optional.of(existingCart));
        when(cartGateway.save(any())).thenAnswer(i -> i.getArgument(0));

        useCase.execute(input);

        verify(inventoryService).validateGeneralStockAvailability(anyLong(), anyInt());

        verify(cartGateway).save(argThat(cart ->
                cart.getItems().size() == 1 &&
                        cart.getItems().getFirst().getProduct().getId() == 1L));

    }

    @Test
    void shouldAddNewItemToCartIfCartExistsAndIsAbandoned() {
        Product product = buildProduct(1L, BigDecimal.valueOf(10.00));

        ShoppingCart existingCart =
                buildCart(1L, ShoppingCartStatus.ABANDONED);

        AddItemShoppingCartIn input =
                new AddItemShoppingCartIn(1L, 1L, 10);

        when(productService.findById(input.productId())).thenReturn(product);
        when(cartGateway.getLastCreatedCart(input.userId()))
                .thenReturn(Optional.of(existingCart));
        when(cartGateway.save(any())).thenAnswer(i -> i.getArgument(0));

        useCase.execute(input);

        verify(inventoryService).validateGeneralStockAvailability(anyLong(), anyInt());

        verify(cartGateway).save(argThat(cart ->
                cart.getItems().size() == 1 &&
                        cart.getItems().getFirst().getProduct().getId() == 1L));
    }

    @Test
    void shouldCreateNewCartAndAddInputItemIfNotExists(){
        Product product = buildProduct(1L, BigDecimal.valueOf(10.00));

        AddItemShoppingCartIn input =
                new AddItemShoppingCartIn(1L, 1L, 10);

        when(productService.findById(input.productId())).thenReturn(product);
        when(cartGateway.getLastCreatedCart(input.userId()))
                .thenReturn(Optional.empty());
        when(cartGateway.save(any())).thenAnswer(i -> i.getArgument(0));

        useCase.execute(input);

        verify(inventoryService).validateGeneralStockAvailability(anyLong(), anyInt());

        verify(cartGateway).save(argThat(cart ->
                cart.getItems().size() == 1 &&
                        cart.getItems().getFirst().getProduct().getId() == 1L));

    }

    @Test
    void shouldCreateNewCartAndAddInputItemIfLastCartFoundProcessed(){
        Product product = buildProduct(1L, BigDecimal.valueOf(10.00));

        ShoppingCart existingCart =
                buildCart(1L, ShoppingCartStatus.PROCESSED);

        AddItemShoppingCartIn input =
                new AddItemShoppingCartIn(1L, 1L, 10);

        when(productService.findById(input.productId())).thenReturn(product);
        when(cartGateway.getLastCreatedCart(input.userId()))
                .thenReturn(Optional.empty());
        when(cartGateway.save(any())).thenAnswer(i -> i.getArgument(0));

        useCase.execute(input);

        verify(inventoryService).validateGeneralStockAvailability(anyLong(), anyInt());

        verify(cartGateway).save(argThat(cart ->
                cart.getItems().size() == 1 &&
                        cart.getItems().getFirst().getProduct().getId() == 1L));

    }
}