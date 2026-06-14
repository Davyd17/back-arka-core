package com.arka.controller;

import com.arka.mappers.EmailRestMapper;
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
 * Access to this controller is restricted to the internal network port
 * via the security filter layer.
 * </p>
 */
@RestController
@RequestMapping(path = "api/v1/reports/internal")
@RequiredArgsConstructor
public class InternalReportController {

    private final SendWeeklySalesReportUseCase salesReportUseCase;
    private final EmailRestMapper mapper;

    @PostMapping("/sales/weekly")
    public ResponseEntity<String> triggerWeeklySalesReport(
            @RequestParam(defaultValue = "CSV") ExportFormat format,
            @Valid @RequestBody EmailMessageRequest emailRequest) {

        try {

            salesReportUseCase.execute(mapper.toDomain(emailRequest), format);
            return ResponseEntity.ok(
                    "Weekly report generated and email sent successfully.");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to generate report: " + e.getMessage());
        }
    }
}

