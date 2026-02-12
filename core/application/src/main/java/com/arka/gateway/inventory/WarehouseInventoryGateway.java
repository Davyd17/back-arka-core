package com.arka.gateway.inventory;

import com.arka.model.inventory.WarehouseInventory;

import java.util.Optional;

public interface WarehouseInventoryGateway {

    Optional<WarehouseInventory> findInventoryByWarehouseAndProduct(Long locationId, Long productId);

    WarehouseInventory save(WarehouseInventory inventory);
}
