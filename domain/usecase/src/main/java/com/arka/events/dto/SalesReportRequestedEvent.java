package com.arka.events.dto;

import com.arka.report.ExportFormat;

public record SalesReportRequestedEvent(
        String sender,
        String recipient,
        String subject,
        String body,
        ExportFormat format
) {
}
