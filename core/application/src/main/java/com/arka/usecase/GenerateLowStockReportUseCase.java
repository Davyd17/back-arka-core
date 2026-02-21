package com.arka.usecase;

import com.arka.dto.out.LowStockReportOut;
import com.arka.dto.value.LowStockItem;
import com.arka.exceptions.NotFoundException;
import com.arka.mapper.WarehouseInventoryMapperImpl;
import com.arka.gateway.repository.inventory.WarehouseInventoryGateway;
import com.arka.mapper.WarehouseInventoryMapper;
import com.arka.service.WarehouseService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class GenerateLowStockReportUseCase {

    private final WarehouseInventoryGateway inventoryGateway;
    private final WarehouseInventoryMapper mapper =
            new WarehouseInventoryMapperImpl();

    private final WarehouseService warehouseService;

    public LowStockReportOut execute(Long warehouseId, int threshold) {

        if (Objects.nonNull(warehouseId) && threshold > 0) {

            warehouseService.findById(warehouseId);

            List<LowStockItem> items = inventoryGateway
                    .listLowStockInventoryByWarehouseId(warehouseId, threshold)
                    .stream()
                    .map(mapper::toOutDTO)
                    .toList();

            if (items.isEmpty()) {

                throw new NotFoundException(
                        "No low stock items found for warehouse with id " + warehouseId
                );

            } else return new LowStockReportOut(items);

        } else throw new IllegalArgumentException(
                "Warehouse inventory id cannot be null"
        );
    }
}
