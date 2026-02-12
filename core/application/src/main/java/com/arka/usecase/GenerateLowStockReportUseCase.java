package com.arka.usecase;

import com.arka.dto.out.WarehouseInventoryOut;
import com.arka.gateway.inventory.WarehouseInventoryGateway;
import com.arka.mapper.ShippingDetailMapper;
import com.arka.mapper.WarehouseInventoryMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class GenerateLowStockReportUseCase {

    private final WarehouseInventoryGateway inventoryGateway;
    private WarehouseInventoryMapper mapper;

    public List<WarehouseInventoryOut> execute(Long warehouseInventoryId, int threshold) {

        if(Objects.nonNull(warehouseInventoryId) && threshold > 0){

            return inventoryGateway
                    .listLowStockInventoryByWarehouseId(warehouseInventoryId, threshold)
                    .stream()
                    .map(mapper::toOutDTO)
                    .toList();

        } else throw new IllegalArgumentException(
                "Warehouse inventory id cannot be null"
        );
    }
}
