package com.arka.usecase.report;

import com.arka.dto.out.LowStockReportOut;
import com.arka.dto.value.LowStockItem;
import com.arka.exceptions.NotFoundException;
import com.arka.mapper.WarehouseInventoryMapperImpl;
import com.arka.gateway.inventory.WarehouseInventoryGateway;
import com.arka.mapper.WarehouseInventoryMapper;
import com.arka.service.WarehouseService;
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
