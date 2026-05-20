package com.arka.report;

import com.arka.inventory.mapper.WarehouseInventoryMapperImpl;
import com.arka.report.dto.LowStockReportOut;
import com.arka.report.dto.LowStockItem;
import com.arka.exceptions.NotFoundException;
import com.arka.inventory.gateway.WarehouseInventoryGateway;
import com.arka.inventory.mapper.WarehouseInventoryMapper;
import com.arka.inventory.service.WarehouseService;
import com.arka.util.NullValidator;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GenerateLowStockReportUseCase {

    private final WarehouseInventoryGateway inventoryGateway;
    private final WarehouseInventoryMapper mapper =
            new WarehouseInventoryMapperImpl();

    private final WarehouseService warehouseService;

    public LowStockReportOut execute(Long warehouseId, int threshold) {

        NullValidator.validate(warehouseId, "warehouseId");

        if(threshold < 0 )
            throw new IllegalArgumentException("Threshold should be greater than 0");

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
    }
}
