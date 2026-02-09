package com.arka.service;

import com.arka.exceptions.NotFoundException;
import com.arka.gateway.inventory.WarehouseInventoryGateway;
import com.arka.model.inventory.WarehouseInventory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WarehouseInventoryService {

    private final WarehouseInventoryGateway gateway;

    private final ProductService productService;
    private final WarehouseService warehouseService;

    public WarehouseInventory findByProductAndWarehouse(Long productId, Long warehouseId) {

        productService.findById(productId);
        warehouseService.findById(warehouseId);

        return gateway.findInventoryByWarehouseAndProduct(productId, warehouseId)
                .orElseThrow(() -> new NotFoundException(
                        String.format(
                                "No inventory found for product %d in warehouse %d",
                                productId, warehouseId)
                ));
    }
}
