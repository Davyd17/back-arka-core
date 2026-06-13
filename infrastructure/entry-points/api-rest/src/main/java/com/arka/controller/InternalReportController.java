package com.arka.controller;

import com.arka.mappers.EmailRestMapper;
import com.arka.notification.SendWeeklySalesReportUseCase;
import com.arka.report.ExportFormat;
import com.arka.request.EmailMessageRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/reports/internal")
@RequiredArgsConstructor
public class InternalReportController {

    @Value("${security.internal-api.key}")
    private String INTERNAL_TRIGGER_TOKEN;

    private final SendWeeklySalesReportUseCase salesReportUseCase;
    private final EmailRestMapper mapper;

    @PostMapping("/sales/weekly")
    public ResponseEntity<String> triggerWeeklySalesReport(
            @RequestHeader(value = "X-Internal-Trigger", required = false) String token,
            @RequestParam(defaultValue = "CSV") ExportFormat format,
            @Valid @RequestBody EmailMessageRequest emailRequest) {

        if (isTokenInvalid(token))
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Unauthorized trigger attempt.");

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

    private boolean isTokenInvalid(String token){
        return token == null || !token.equals(INTERNAL_TRIGGER_TOKEN);
    }
}

