package com.arka.events.dto;

import com.arka.report.ExportFormat;

public record LowStockReportRequestedEvent(
        int threshold,
        ExportFormat attachmentFormat,
        Long warehouseId,
        String recipient
) {
}
