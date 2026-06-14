package com.arka.report;

import com.arka.report.dto.SalesReportData;
import com.arka.report.gateway.ExportGateway;
import com.arka.report.service.SalesReportService;
import com.arka.util.NullValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GenerateSalesReportUseCase {

    private final SalesReportService salesReportService;

    private final ExportGateway exportGateway;

    public byte[] execute(ExportFormat format){

        NullValidator.validate(format, "ExportFormat");

        SalesReportData data = salesReportService.getWeekSalesReport();

        return exportGateway.export(data, format);
    }
}
