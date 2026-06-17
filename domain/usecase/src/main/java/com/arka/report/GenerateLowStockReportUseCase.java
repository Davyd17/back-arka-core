package com.arka.report;

import com.arka.report.dto.LowStockReportData;
import com.arka.report.gateway.ExportGateway;
import com.arka.report.service.StockDataService;
import com.arka.util.NullValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GenerateLowStockReportUseCase {

    private final ExportGateway exportGateway;

    private final StockDataService stockDataService;

    public byte[] execute(Long warehouseId, int threshold, ExportFormat format) {

        NullValidator.validate(format, "ExportFormat");

        LowStockReportData data = stockDataService
                .getLowStockByWarehouse(warehouseId, threshold);

        return exportGateway.export(data, format);
    }

}
