package com.arka.controller;

import com.arka.events.RequestLowStockReportUseCase;
import com.arka.events.RequestWeekSalesReportUseCase;
import com.arka.report.ExportFormat;
import com.arka.report.dto.LowStockReportCommand;
import com.arka.report.dto.SalesReportCommand;
import com.arka.request.EmailMessageRequest;
import com.arka.response.AppResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    private final RequestWeekSalesReportUseCase requestWeekSalesReportUseCase;
    private final RequestLowStockReportUseCase requestLowStockReportUseCase;

    @Operation(
            summary = "[INTERNAL] Trigger weekly sales report",
            description = "**Restricted**: Enqueues an asynchronous request to generate and email a" +
                    "weekly sales report. Accessible only via internal network/port."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "202",
                    description = "Report generation request accepted for asynchronous processing",
                    content = @Content(schema = @Schema(implementation = AppResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request payload",
                    content = @Content(schema = @Schema(implementation = AppResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Failed to accept report generation request",
                    content = @Content(schema = @Schema(implementation = AppResponse.class))
            )
    })
    @PostMapping("/sales/weekly")
    public ResponseEntity<AppResponse<String>> triggerWeeklySalesReport(
            @Parameter(description = "Export attachmentFormat for the generated report", example = "CSV")
            @RequestParam(defaultValue = "CSV") ExportFormat format,
            @Valid @RequestBody EmailMessageRequest emailMessageRequest) {

        requestWeekSalesReportUseCase.execute(new SalesReportCommand(
                emailMessageRequest.recipient(), format));

        return ResponseEntity.accepted().body(
                AppResponse.success(
                        "REPORT_REQUESTED",
                        "Sales report generation and email delivery have been requested"
                ));
    }


    @Operation(
            summary = "[INTERNAL] Request weekly low stock report",
            description = "**Restricted**: Enqueues an asynchronous request to generate and email low stock alerts " +
                    "for a specific warehouse. Accessible only via internal network/port."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "202",
                    description = "Low stock report request accepted and queued for processing",
                    content = @Content(schema = @Schema(implementation = AppResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request — missing or malformed email data"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Failed to enqueue report request"
            )
    })
    @PostMapping("warehouse/{warehouseId}/low-stock/weekly")
    public ResponseEntity<AppResponse<String>> triggerWeeklyLowStockReport(
            @Parameter(description = "Stock quantity threshold trigger", example = "40")
            @RequestParam(defaultValue = "40") int threshold,
            @Parameter(description = "Export attachmentFormat for the generated report", example = "CSV")
            @RequestParam(defaultValue = "CSV") ExportFormat format,
            @PathVariable Long warehouseId,
            @Valid @RequestBody EmailMessageRequest emailMessageRequest
    ) {
        requestLowStockReportUseCase.execute(new LowStockReportCommand(
                emailMessageRequest.recipient(),
                format,
                warehouseId,
                threshold));

        return ResponseEntity.accepted().body(AppResponse.success(
                "REPORT_REQUESTED",
                "Low stock report generation and email delivery have been requested"
        ));
    }
}

