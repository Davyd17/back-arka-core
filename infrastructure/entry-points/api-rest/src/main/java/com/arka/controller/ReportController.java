package com.arka.controller;

import com.arka.report.dto.LowStockReportOut;
import com.arka.report.dto.SalesReportOut;
import com.arka.report.GenerateLowStockReportUseCase;
import com.arka.report.GenerateSalesReportUseCase;
import com.arka.util.export.ExportFormat;
import com.arka.util.export.ExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Exposes report generation endpoints.
 * All responses are downloadable files in the requested format (CSV by default).
 */
@RestController
@RequestMapping(path = "/api/v1/reports")
@RequiredArgsConstructor
public class ReportController {

    private final GenerateSalesReportUseCase generateSalesReportUseCase;

    private final GenerateLowStockReportUseCase generateLowStockReportUseCase;

    private final ExportService exportService;

    /**
     * Generates a low stock report for a specific warehouse.
     *
     * @param warehouseInventoryId the warehouse inventory ID to report on
     * @param threshold            products with stock below this value are included (default: 40)
     * @param format               export format — CSV or PDF (default: CSV)
     * @return downloadable file with low stock items
     */
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


    /**
     * Generates a sales report for the last 7 days.
     *
     * @param format export format — CSV or PDF (default: CSV)
     * @return downloadable file with sales data
     */
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
