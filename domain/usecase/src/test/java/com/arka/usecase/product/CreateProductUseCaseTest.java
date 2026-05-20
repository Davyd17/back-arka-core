package com.arka.usecase.product;

import com.arka.product.CreateProductUseCase;
import com.arka.product.dto.CreateProductIn;
import com.arka.gateway.product.ProductGateway;
import com.arka.model.product.ProductCategory;
import com.arka.product.service.ProductCategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateProductUseCaseTest {

    @Mock
    private ProductGateway productGateway;

    @Mock
    private ProductCategoryService categoryService;

    @InjectMocks
    private CreateProductUseCase useCase;

    private ProductCategory category;

    @BeforeEach
    void setUp(){
        category = ProductCategory.create("Electronics");
    }

    @Test
    void shouldThrowWhenInputIsNull(){

        assertThrows(IllegalArgumentException.class, () ->
                useCase.execute(null));
    }

    // --- product creation ---

    @Test
    void shouldCreateProductWithCorrectFields() {
        CreateProductIn input = new CreateProductIn(
                "SKU-001", "Product", 1L, null,
                BigDecimal.valueOf(10.00), null);

        when(categoryService.findById(1L)).thenReturn(category);
        when(productGateway.create(any()))
                .thenAnswer(i -> i.getArgument(0));

        useCase.execute(input);

        verify(productGateway).create(argThat(product ->
                product.getSku().equals("SKU-001") &&
                        product.isActive() &&
                        product.getCategory().equals(category)
        ));
    }

    @Test
    void shouldAddAttributesWhenProvided() {
        CreateProductIn input = new CreateProductIn(
                "SKU-001", "Product", 1L, null,
                BigDecimal.valueOf(10.00),
                Map.of("color", "red", "size", "XL"));

        when(categoryService.findById(1L)).thenReturn(category);
        when(productGateway.create(any()))
                .thenAnswer(i -> i.getArgument(0));

        useCase.execute(input);

        verify(productGateway).create(argThat(product ->
                product.getAttributes().containsKey("color") &&
                        product.getAttributes().containsKey("size")));
    }

    @Test
    void shouldSkipAttributesWhenNull() {
        CreateProductIn input = new CreateProductIn(
                "SKU-001", "Product", 1L, null,
                BigDecimal.valueOf(10.00), null);

        when(categoryService.findById(1L)).thenReturn(category);
        when(productGateway.create(any()))
                .thenAnswer(i -> i.getArgument(0));

        useCase.execute(input);

        verify(productGateway).create(argThat(product ->
                product.getAttributes().isEmpty()));
    }

    // --- persistence ---

    @Test
    void shouldAlwaysCallGatewayCreate() {
        CreateProductIn input = new CreateProductIn(
                "SKU-001", "Product", 1L, null,
                BigDecimal.valueOf(10.00), null);

        when(categoryService.findById(1L)).thenReturn(category);
        when(productGateway.create(any()))
                .thenAnswer(i -> i.getArgument(0));

        useCase.execute(input);

        verify(productGateway).create(any());

    }

}