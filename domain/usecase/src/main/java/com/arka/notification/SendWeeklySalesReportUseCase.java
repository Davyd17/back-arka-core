package com.arka.notification;

import com.arka.notification.dto.EmailAttachment;
import com.arka.notification.dto.EmailMessage;
import com.arka.notification.dto.MessageSubject;
import com.arka.notification.gateway.EmailGateway;
import com.arka.report.ExportFormat;
import com.arka.report.dto.SalesReportCommand;
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

    public void execute(SalesReportCommand command){

        validateCommand(command);

        SalesReportData salesData = salesReportService.getWeekSalesReport();
        byte[] exportedData = exportGateway.export(salesData,command.attachmentFormat());

        EmailMessage emailMessage = buildEmail(
                exportedData, command.attachmentFormat(), command.recipient());

        emailGateway.send(emailMessage);
    }

    private EmailMessage buildEmail(byte[] exportedData,
                                    ExportFormat attachmentFormat,
                                    String recipient){

        EmailAttachment attachment = new EmailAttachment(
                exportedData,
                "weekly-sales-report",
                attachmentFormat);

        return new EmailMessage(
                recipient,
                MessageSubject.WEEK_SALES_REPORT).toBuilder()
                .attachment(attachment).build();
    }

    private void validateCommand(SalesReportCommand command){
        NullValidator.validate(command.attachmentFormat(), "Attachment Format");
        NullValidator.validate(command.recipient(), "Recipient");
    }
}
