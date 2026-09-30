package com.arka.notification;

import com.arka.notification.dto.EmailAttachment;
import com.arka.notification.dto.EmailMessage;
import com.arka.notification.dto.MessageSubject;
import com.arka.notification.gateway.EmailGateway;
import com.arka.report.ExportFormat;
import com.arka.report.dto.LowStockReportCommand;
import com.arka.report.dto.LowStockReportData;
import com.arka.report.gateway.ExportGateway;
import com.arka.report.service.StockDataService;
import com.arka.util.NullValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SendWeeklyLowStockReportUseCase {

    private final ExportGateway exportGateway;
    private final EmailGateway emailGateway;
    private final StockDataService stockDataService;

    public void execute(LowStockReportCommand command){

        validateInput(command);

        LowStockReportData data = stockDataService
                .getLowStockByWarehouse(command.warehouseId(), command.threshold());

        byte[] formattedData = exportGateway.export(data, command.attachmentFormat());

        EmailMessage email = buildEmail(
                command.recipient(), command.attachmentFormat(), formattedData);

        emailGateway.send(email);
    }

    private EmailMessage buildEmail(String recipient,
                                    ExportFormat attachmentFormat,
                                    byte[] formattedData){

        EmailAttachment attachment = new EmailAttachment(
                formattedData,
                "low-stock-weekly-report",
                attachmentFormat);

        return new EmailMessage(recipient, MessageSubject.LOW_STOCK_REPORT)
                .toBuilder().attachment(attachment).build();
    }

    private void validateInput(LowStockReportCommand command){
        NullValidator.validate(command, "LowStockReportCommand");
        NullValidator.validate(command.attachmentFormat(), "AttachmentFormat");
    }
}
