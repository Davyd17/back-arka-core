package com.arka.sqs;

import com.arka.events.dto.SalesReportRequestedEvent;
import com.arka.events.gateway.ReportsEventPublisherGateway;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class SqsReportsEventPublisherAdapter implements ReportsEventPublisherGateway {

    private final SqsTemplate sqsTemplate;

    @Value("${cloud-provider.aws.sqs.sales-report-queue}")
    private String salesReportQueueName;

    @Override
    public void publishSalesReport(SalesReportRequestedEvent event) {

        log.info("Publishing SalesReportRequestedEvent to SQS for email: {}",
                event.recipient());

        sqsTemplate.send(to -> to.queue(salesReportQueueName).payload(event));
    }
}
