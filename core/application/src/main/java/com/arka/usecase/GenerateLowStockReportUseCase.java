package com.arka.usecase;

import com.arka.dto.out.LowStockItemOut;
import com.arka.exceptions.NotFoundException;
import com.arka.mapper.WarehouseInventoryMapperImpl;
import com.arka.repository.inventory.WarehouseInventoryGateway;
import com.arka.mapper.WarehouseInventoryMapper;
import com.arka.service.WarehouseService;
import com.arka.service.export.ExportFormat;
import com.arka.service.export.LowStockInventoryCsvExporterService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class GenerateLowStockReportUseCase {

    private final WarehouseInventoryGateway inventoryGateway;
    private final WarehouseInventoryMapper mapper =
            new WarehouseInventoryMapperImpl();

    private final WarehouseService warehouseService;

    public byte[] execute(Long warehouseId,
                          int threshold,
                          ExportFormat format) {

        if (Objects.nonNull(warehouseId) && threshold > 0) {

            warehouseService.findById(warehouseId);

            List<LowStockItemOut> lowStockItems = inventoryGateway
                    .listLowStockInventoryByWarehouseId(warehouseId, threshold)
                    .stream()
                    .map(mapper::toOutDTO)
                    .toList();

            if (lowStockItems.isEmpty()) {

                throw new NotFoundException(
                        "No low stock items found for warehouse with id " + warehouseId
                );

            } else return ReportFormatTo(format, lowStockItems);

        } else throw new IllegalArgumentException(
                "Warehouse inventory id cannot be null"
        );
    }

    private byte[] ReportFormatTo(ExportFormat format, List<LowStockItemOut> lowStockItems) {

        if (format == ExportFormat.CSV) {

            return LowStockInventoryCsvExporterService.exportToCsv(lowStockItems);

        } else throw new IllegalArgumentException(
                String.format("Unsupported export format: %s", format)
        );

    }
}
