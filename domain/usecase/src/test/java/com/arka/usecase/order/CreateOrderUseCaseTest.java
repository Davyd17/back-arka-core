package com.arka.usecase.order;

import com.arka.order.CreateOrderUseCase;
import com.arka.order.dto.CreateOrderIn;
import com.arka.enums.CompanyRelationType;
import com.arka.enums.OrderType;
import com.arka.gateway.order.OrderGateway;
import com.arka.order.mapper.OrderMapper;
import com.arka.model.Company;
import com.arka.model.product.Product;
import com.arka.model.product.ProductCategory;
import com.arka.party.service.CompanyService;
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
    private OrderMapper orderMapper;

    @Mock
    private CompanyService companyService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private CreateOrderUseCase useCase;

    private Company company;

    @BeforeEach
    void setUp(){
        company = new Company(1L, "Test Company", CompanyRelationType.CUSTOMER,
                new ArrayList<>(), new ArrayList<>());
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

    @Test
    void shouldCreateOrderWithInputItems(){
        //Arrange
        Product product = buildProduct(1L, BigDecimal.valueOf(10.00));

        CreateOrderIn input = new CreateOrderIn(
                "001",
                null,
                OrderType.SALES,
                1L,
                List.of(new CreateOrderIn.Item(1L, 10)));

        when(companyService.findById(1L)).thenReturn(company);

        when(productService.findById(1L)).thenReturn(product);

        when(orderGateway.save(any()))
                .thenAnswer(i -> i.getArgument(0));

        //Act
        useCase.execute(input);

        //Assert
        verify(orderGateway).save(argThat(order ->
                order.getItems().size() == 1 &&
                order.getItems().getFirst().getQuantity() == 10));
    }

    @Test
    void shouldAlwaysCallGatewaySave(){

        //Arrange
        Product product = buildProduct(1L, BigDecimal.valueOf(10.00));

        CreateOrderIn input = new CreateOrderIn(
                "001",
                null,
                OrderType.SALES,
                1L,
                List.of(new CreateOrderIn.Item(1L, 10)));

        when(companyService.findById(1L)).thenReturn(company);

        when(productService.findById(1L)).thenReturn(product);

        when(orderGateway.save(any()))
                .thenAnswer(i -> i.getArgument(0));

        //Act
        useCase.execute(input);

        //Assert
        verify(orderGateway).save(any());

    }
}