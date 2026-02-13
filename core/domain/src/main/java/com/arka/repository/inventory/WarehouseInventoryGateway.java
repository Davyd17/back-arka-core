package com.arka.repository.inventory;

import com.arka.model.inventory.WarehouseInventory;

import java.util.List;
import java.util.Optional;

public interface WarehouseInventoryGateway {

    Optional<WarehouseInventory> findInventoryByWarehouseAndProduct(Long locationId, Long productId);

    WarehouseInventory save(WarehouseInventory inventory);

    List<WarehouseInventory> listLowStockInventoryByWarehouseId(
            Long warehouseId, int threshold);
}
