package com.arka.notification.dto;

import com.arka.report.ExportFormat;

public record EmailAttachment (
        byte[] data,
        String attachmentName,
        ExportFormat format
) {
}
