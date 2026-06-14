package com.arka.notification;

import com.arka.notification.dto.EmailAttachment;
import com.arka.notification.dto.EmailMessage;
import com.arka.report.ExportFormat;
import com.arka.report.dto.SalesReportData;
import com.arka.report.gateway.ExportGateway;
import com.arka.report.service.SalesReportService;
import com.arka.util.NullValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SendWeeklySalesReportUseCase {

    private final SalesReportService salesReportService;
    private final ExportGateway exportGateway;
    private final EmailGateway emailGateway;

    public void execute(EmailMessage email, ExportFormat format){

        NullValidator.validate(email, "Input");
        NullValidator.validate(format, "ExportFormat");

        SalesReportData salesData = salesReportService.getWeekSalesReport();
        byte[] exportedData = exportGateway.export(salesData, format);

        EmailAttachment attachment = new EmailAttachment(
                exportedData,
                "weekly-sales-report",
                format);

        emailGateway.send(email, attachment);
    }
}
