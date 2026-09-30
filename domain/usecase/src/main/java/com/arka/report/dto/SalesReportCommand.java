package com.arka.report.dto;

import com.arka.report.ExportFormat;

public record SalesReportCommand(
        String recipient,
        ExportFormat attachmentFormat
) {
}
