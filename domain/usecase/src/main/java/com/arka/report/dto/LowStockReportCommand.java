package com.arka.report.dto;

import com.arka.report.ExportFormat;

public record LowStockReportCommand(
        String recipient,
        ExportFormat attachmentFormat,
        Long warehouseId,
        int threshold
) {
}
