package com.arka;

import com.arka.notification.SendWeeklySalesReportUseCase;
import com.arka.events.dto.SalesReportRequestedEvent;
import com.arka.notification.dto.EmailMessage;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class SqsReportsListener {

    private final SendWeeklySalesReportUseCase salesReportUseCase;

    @SqsListener("${cloud-provider.aws.sqs.sales-report-queue}")
    public void handleSalesReportEvent(SalesReportRequestedEvent event){

        log.info("Received SalesReportRequestedEvent from SQS for email: {}",
                event.recipient());

        salesReportUseCase.execute(buildEmail(event), event.format());

        log.info("Successfully processed week sales report task for: {}",
                event.recipient());
    }

    private EmailMessage buildEmail(SalesReportRequestedEvent event){
        return new EmailMessage(
                event.sender(),
                event.recipient(),
                event.subject(), event.body()
        );
    }
}
