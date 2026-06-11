package com.arka.report;

import com.arka.report.dto.LowStockReportData;
import com.arka.report.dto.LowStockItem;
import com.arka.exceptions.NotFoundException;
import com.arka.inventory.gateway.WarehouseInventoryGateway;
import com.arka.inventory.mapper.WarehouseInventoryMapper;
import com.arka.inventory.service.WarehouseService;
import com.arka.report.gateway.ExportGateway;
import com.arka.util.NullValidator;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;

import java.util.List;

@RequiredArgsConstructor
public class GenerateLowStockReportUseCase {

    private final WarehouseInventoryGateway inventoryGateway;
    private final ExportGateway exportGateway;
    private final WarehouseInventoryMapper mapper =
            Mappers.getMapper(WarehouseInventoryMapper.class);

    private final WarehouseService warehouseService;

    public byte[] execute(Long warehouseId, int threshold, ExportFormat format) {

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

        } else return exportGateway.export(new LowStockReportData(items), format);
    }
}
