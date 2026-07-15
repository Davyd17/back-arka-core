package com.arka.inventory.service;

import com.arka.exceptions.InsufficientStockException;
import com.arka.inventory.gateway.WarehouseInventoryGateway;
import com.arka.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WarehouseInventoryServiceTest {

    @Mock
    private WarehouseInventoryGateway warehouseInventoryGateway;

    @Mock
    private ProductService productService;

    @Mock
    private WarehouseService warehouseService;

    @InjectMocks
    private WarehouseInventoryService warehouseInventoryService;

    @Test
    void shouldThrowWhenDesiredStockExceedActual() {

        when(warehouseInventoryGateway.getTotalStockByProductId(1L)).thenReturn(30);

        assertThrows(InsufficientStockException.class,
                () -> warehouseInventoryService
                        .validateGeneralStockAvailability(1L, 40));
    }

    @Test
    void shouldNotThrowWhenStockIsAvailable() {

        when(warehouseInventoryGateway.getTotalStockByProductId(1L)).thenReturn(30);

        assertDoesNotThrow(() -> warehouseInventoryService
                        .validateGeneralStockAvailability(1L, 20));
    }
}