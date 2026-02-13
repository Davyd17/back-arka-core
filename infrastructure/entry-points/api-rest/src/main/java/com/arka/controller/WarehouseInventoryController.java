package com.arka.controller;

import com.arka.service.export.ExportFormat;
import com.arka.usecase.GenerateLowStockReportUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/warehouses-inventory")
@RequiredArgsConstructor
public class WarehouseInventoryController {

    private final GenerateLowStockReportUseCase generateLowStockReportUseCase;

    @GetMapping("{warehouseInventoryId}/low-stock")
    public final ResponseEntity<byte[]> getLowStockReport(
            @PathVariable Long warehouseInventoryId,
            @RequestParam(defaultValue = "40") int threshold){

        byte[] csvReport = generateLowStockReportUseCase
                .execute(warehouseInventoryId, threshold, ExportFormat.CSV);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=low_stock_inventory.csv")
                .contentType(MediaType.TEXT_PLAIN)
                .body(csvReport);
    }

}
