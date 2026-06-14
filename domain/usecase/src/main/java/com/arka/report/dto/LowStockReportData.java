package com.arka.report.dto;

import java.util.List;

public record LowStockReportData(
        List<LowStockItem> items
) {
}
