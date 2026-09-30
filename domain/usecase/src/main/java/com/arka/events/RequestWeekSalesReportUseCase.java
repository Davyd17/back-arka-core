package com.arka.events;

import com.arka.events.dto.SalesReportRequestedEvent;
import com.arka.events.gateway.ReportsEventPublisherGateway;
import com.arka.report.dto.SalesReportCommand;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RequestWeekSalesReportUseCase {

    private final ReportsEventPublisherGateway reportsEventPublisher;

    public void execute(SalesReportCommand command){

        reportsEventPublisher.publishSalesReport(new SalesReportRequestedEvent(
                command.recipient(),
                command.attachmentFormat()
        ));
    }
}
