package com.arka.controller;

import com.arka.dto.out.LowStockReportOut;
import com.arka.dto.out.SalesReportOut;
import com.arka.usecase.GenerateLowStockReportUseCase;
import com.arka.usecase.GenerateSalesReportUseCase;
import com.arka.util.export.ExportFormat;
import com.arka.util.export.ExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/reports")
@RequiredArgsConstructor
public class ReportController {

    private final GenerateSalesReportUseCase generateSalesReportUseCase;

    private final GenerateLowStockReportUseCase generateLowStockReportUseCase;

    private final ExportService exportService;



    @GetMapping("warehouse/{warehouseInventoryId}/low-stock")
    public final ResponseEntity<byte[]> getLowStockReport(
            @PathVariable Long warehouseInventoryId,
            @RequestParam(defaultValue = "40") int threshold,
            @RequestParam(defaultValue = "CSV") ExportFormat format){

        LowStockReportOut lowStockReport = generateLowStockReportUseCase
                .execute(warehouseInventoryId, threshold);

        byte[] file = exportService.export(format, lowStockReport);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=low-stock-report."
                                + format.name().toLowerCase())

                .contentType(format.getMediaType())
                .body(file);
    }

    @GetMapping("sales/seven-days-ago")
    public ResponseEntity<byte[]>  generate7DaysSalesReport(
            @RequestParam(defaultValue = "CSV") ExportFormat format
    ){
        SalesReportOut salesReport = generateSalesReportUseCase.execute();

        byte[] file = exportService.export(format, salesReport);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=sales-report."
                                + format.name().toLowerCase())

                .contentType(format.getMediaType())
                .body(file);
    }


}
