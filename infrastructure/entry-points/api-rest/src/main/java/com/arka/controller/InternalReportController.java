package com.arka.controller;

import com.arka.mappers.EmailRestMapper;
import com.arka.notification.SendWeeklyLowStockReportUseCase;
import com.arka.notification.SendWeeklySalesReportUseCase;
import com.arka.report.ExportFormat;
import com.arka.request.EmailMessageRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * REST controller providing administrative endpoints for triggering internal reports.
 * <p>
 * Access to this controller is restricted to the internal network previous specified
 * port via the security filter layer.
 * </p>
 */
@RestController
@RequestMapping(path = "api/v1/reports/internal")
@RequiredArgsConstructor
public class InternalReportController {

    private final SendWeeklySalesReportUseCase salesReportUseCase;
    private final SendWeeklyLowStockReportUseCase lowStockReportUseCase;
    private final EmailRestMapper emailMapper;

    @PostMapping("/sales/weekly")
    public ResponseEntity<String> triggerWeeklySalesReport(
            @RequestParam(defaultValue = "CSV") ExportFormat format,
            @Valid @RequestBody EmailMessageRequest emailRequest) {

        try {

            salesReportUseCase.execute(emailMapper.toDomain(emailRequest), format);
            return ResponseEntity.ok(
                    "Weekly report generated and email sent successfully.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to generate report: " + e.getMessage());
        }
    }

    @PostMapping("warehouse/{warehouseId}/low-stock/weekly")
    public ResponseEntity<String> triggerWeeklyLowStockReport(
            @RequestParam(defaultValue = "40") int threshold,
            @RequestParam(defaultValue = "CSV") ExportFormat format,
            @PathVariable Long warehouseId,
            @Valid @RequestBody EmailMessageRequest emailMessageRequest
    ){

        try{

            lowStockReportUseCase.execute(
                    emailMapper.toDomain(emailMessageRequest),
                    format,
                    warehouseId,
                    threshold);

            return ResponseEntity.ok(
                    "Weekly report generated and email sent successfully.");

        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to generate report: " + e.getMessage());
        }

    }
}

