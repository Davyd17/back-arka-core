package com.arka.report.service;

import com.arka.exceptions.NotFoundException;
import com.arka.inventory.gateway.WarehouseInventoryGateway;
import com.arka.inventory.mapper.WarehouseInventoryMapper;
import com.arka.inventory.service.WarehouseService;
import com.arka.report.dto.LowStockItem;
import com.arka.report.dto.LowStockReportData;
import com.arka.util.NullValidator;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;

import java.util.List;

@RequiredArgsConstructor
public class StockDataService {

    private final WarehouseService warehouseService;

    private final WarehouseInventoryGateway inventoryGateway;

    private final WarehouseInventoryMapper mapper =
            Mappers.getMapper(WarehouseInventoryMapper.class);

    public LowStockReportData getLowStockByWarehouse(Long warehouseId,
                                                     int threshold) {

        validateArgs(warehouseId, threshold);

        List<LowStockItem> items = inventoryGateway
                .listLowStockInventoryByWarehouseId(warehouseId, threshold)
                .stream()
                .map(mapper::toOutDTO)
                .toList();

        if (items.isEmpty())
            throw new NotFoundException(
                    "No low stock items found for warehouse with id " + warehouseId
            );

        return new LowStockReportData(items);
    }

    private void validateArgs(Long warehouseId, int threshold){

        NullValidator.validate(warehouseId, "warehouseId");

        if(threshold < 0 )
            throw new IllegalArgumentException("Threshold should be greater than 0");

        warehouseService.findById(warehouseId);
    }
}
