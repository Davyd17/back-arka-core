package com.arka.notification;

import com.arka.notification.dto.EmailAttachment;
import com.arka.notification.dto.EmailMessage;
import com.arka.notification.gateway.EmailGateway;
import com.arka.report.ExportFormat;
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

    public void execute(EmailMessage email,
                        ExportFormat attachmentFormat,
                        Long warehouseId,
                        int threshold){

        validateInput(email, attachmentFormat);

        LowStockReportData data = stockDataService
                .getLowStockByWarehouse(warehouseId, threshold);

        byte[] exportedData = exportGateway.export(data, attachmentFormat);

        EmailAttachment attachment = new EmailAttachment(
                exportedData,
                "low-stock-weekly-report",
                attachmentFormat);

        emailGateway.send(email, attachment);
    }

    private void validateInput(EmailMessage email, ExportFormat attachmentFormat){
        NullValidator.validate(email, "Email Message");
        NullValidator.validate(attachmentFormat, "AttachmentFormat");
    }
}
