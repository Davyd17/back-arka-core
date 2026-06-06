package com.arka.report.dto;

import java.util.List;

public record LowStockReportOut(
        List<LowStockItem> items
) {
}
