package com.arka.controller;

import com.arka.report.ExportFormat;
import com.arka.report.GenerateLowStockReportUseCase;
import com.arka.report.GenerateSalesReportUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

        byte[] file = generateLowStockReportUseCase.execute(
                warehouseInventoryId,
                threshold,
                format
        );

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=low-stock-report."
                                + format.name().toLowerCase())

                .contentType(MediaType.parseMediaType(format.getMimeType()))
                .body(file);
    }


    /**
     * Generates a sales report for the last 7 days.
     *
     * @param format export format — CSV or PDF (default: CSV)
     * @return downloadable file with sales data
     */
    @GetMapping("sales/seven-days-ago")
    public ResponseEntity<byte[]> generateWeekSalesReport(
            @RequestParam(defaultValue = "CSV") ExportFormat format
    ){
        byte[] file = generateSalesReportUseCase.execute(format);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=sales-report."
                                + format.getFileExtension())
                .contentType(MediaType.parseMediaType(format.getMimeType()))
                .body(file);
    }


}
