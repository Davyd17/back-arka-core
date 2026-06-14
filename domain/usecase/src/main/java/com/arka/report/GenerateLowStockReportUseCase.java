package com.arka.report;

import com.arka.report.dto.LowStockReportData;
import com.arka.inventory.service.WarehouseService;
import com.arka.report.gateway.ExportGateway;
import com.arka.report.service.StockDataService;
import com.arka.util.NullValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GenerateLowStockReportUseCase {

    private final ExportGateway exportGateway;

    private final StockDataService stockDataService;
    private final WarehouseService warehouseService;

    public byte[] execute(Long warehouseId, int threshold, ExportFormat format) {

        validateInput(warehouseId, threshold, format);

        LowStockReportData data = stockDataService
                .getLowStockByWarehouse(warehouseId, threshold);

        return exportGateway.export(data, format);
    }

    private void validateInput(Long warehouseId, int threshold, ExportFormat format){

        NullValidator.validate(warehouseId, "warehouseId");
        NullValidator.validate(format, "ExportFormat");

        if(threshold < 0 )
            throw new IllegalArgumentException("Threshold should be greater than 0");

        warehouseService.findById(warehouseId);
    }
}
