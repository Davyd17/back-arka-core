package com.arka.events;

import com.arka.events.dto.SalesReportRequestedEvent;
import com.arka.events.gateway.ReportsEventPublisherGateway;
import com.arka.notification.dto.EmailMessage;
import com.arka.report.ExportFormat;
import com.arka.util.NullValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RequestWeekSalesReportUseCase {

    private final ReportsEventPublisherGateway reportsEventPublisher;

    public void execute(EmailMessage email, ExportFormat format){

        NullValidator.validate(email, "Email");
        NullValidator.validate(format, "ExportFormat");

        SalesReportRequestedEvent requested = new SalesReportRequestedEvent(
                email.sender(),
                email.recipient(),
                email.subject(),
                email.body(),
                format
        );

        reportsEventPublisher.publishSalesReport(requested);
    }
}
