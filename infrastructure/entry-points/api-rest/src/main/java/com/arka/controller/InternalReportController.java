package com.arka.controller;

import com.arka.mappers.EmailRestMapper;
import com.arka.notification.SendWeeklyLowStockReportUseCase;
import com.arka.notification.SendWeeklySalesReportUseCase;
import com.arka.report.ExportFormat;
import com.arka.request.EmailMessageRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Internal Reports", description = "Internal network operations for scheduled tasks. (Restricted access)")
public class InternalReportController {

    private final SendWeeklySalesReportUseCase salesReportUseCase;
    private final SendWeeklyLowStockReportUseCase lowStockReportUseCase;
    private final EmailRestMapper emailMapper;

    @Operation(
            summary = "[INTERNAL] Trigger weekly sales report",
            description = "**Restricted**: Triggers generation and emailing of weekly sales reports. " +
                    "Accessible only via internal network/port.",
            deprecated = true
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Weekly report generated and email sent successfully",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Failed to generate report"
            )
    })
    @PostMapping("/sales/weekly")
    public ResponseEntity<String> triggerWeeklySalesReport(
            @Parameter(description = "Export format for the generated report", example = "CSV")
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


    @Operation(
            summary = "[INTERNAL] Trigger weekly low stock report",
            description = "**Restricted**: Triggers generation and emailing of low stock alerts for a specific warehouse. " +
                    "Accessible only via internal network/port.",
            deprecated = true
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Weekly low stock report generated successfully",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Failed to generate report"
            )
    })
    @PostMapping("warehouse/{warehouseId}/low-stock/weekly")
    public ResponseEntity<String> triggerWeeklyLowStockReport(
            @Parameter(description = "Stock quantity threshold trigger", example = "40")
            @RequestParam(defaultValue = "40") int threshold,
            @Parameter(description = "Export format for the generated report", example = "CSV")
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

