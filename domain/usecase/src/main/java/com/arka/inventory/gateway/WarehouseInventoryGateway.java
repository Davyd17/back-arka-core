package com.arka.inventory.gateway;

import com.arka.entities.inventory.WarehouseInventory;
import com.arka.report.dto.LowStockReportData;

import java.util.List;
import java.util.Optional;

public interface WarehouseInventoryGateway {

    Optional<WarehouseInventory> findInventoryByWarehouseAndProduct(Long locationId, Long productId);

    WarehouseInventory save(WarehouseInventory inventory);

    List<LowStockReportData.Item> listLowStockInventoryByWarehouseId(
            Long warehouseId, int threshold);

    int getTotalStockByProductId(Long productId);
}
