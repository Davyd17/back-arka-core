package com.arka.controller;

import com.arka.docs.CommonApiResponses;
import com.arka.exceptions.ErrorResponse;
import com.arka.report.ExportFormat;
import com.arka.report.GenerateLowStockReportUseCase;
import com.arka.report.GenerateSalesReportUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/api/v1/reports")
@RequiredArgsConstructor
@Tag(name = "Reports",
        description = "Endpoints for generating and downloading reports")
public class ReportController {

    private final GenerateSalesReportUseCase generateSalesReportUseCase;

    private final GenerateLowStockReportUseCase generateLowStockReportUseCase;


    @Operation(
            summary = "Generate low stock report",
            description = "Generates and downloads a low stock report file (CSV) for a specific warehouse based on the provided threshold."
    )
    @CommonApiResponses
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Report generated successfully as a downloadable file",
                    content = @Content(mediaType = "application/octet-stream")
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid threshold or export format",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Warehouse inventory not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping("warehouse/{warehouseInventoryId}/low-stock")
    public final ResponseEntity<byte[]> getLowStockReport(
            @Parameter(description = "Target warehouse inventory ID", example = "1")
            @PathVariable Long warehouseInventoryId,
            @Parameter(description = "Stock threshold value", example = "40")
            @RequestParam(defaultValue = "40") int threshold,
            @Parameter(description = "Export file format only CSV for now", example = "CSV")
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


    @Operation(
            summary = "Generate 7-day sales report",
            description = "Generates and downloads a sales summary report file for the last 7 days.")
    @CommonApiResponses
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Sales report generated successfully as a downloadable file",
                    content = @Content(mediaType = "application/octet-stream")
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid export format",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping("sales/seven-days")
    public ResponseEntity<byte[]> generateWeekSalesReport(
            @Parameter(description = "Export file format only CSV for now", example = "CSV")
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
