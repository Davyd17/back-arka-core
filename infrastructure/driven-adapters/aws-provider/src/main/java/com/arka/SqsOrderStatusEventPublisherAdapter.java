package com.arka;

import com.arka.notification.dto.OrderStatusChangeRequestedEvent;
import com.arka.notification.gateway.OrderStatusEventPublisherGateway;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SqsOrderStatusEventPublisherAdapter implements OrderStatusEventPublisherGateway {

    private final SqsTemplate sqsTemplate;

    @Value("${cloud-provider.aws.sqs.events-queue}")
    private String queueName;

    @Override
    public void publish(OrderStatusChangeRequestedEvent event) {
        log.info("Publishing OrderStatusChangeRequestedEvent to SQS for email: {}", event.recipient());
        sqsTemplate.send(to -> to.queue(queueName).payload(event));
    }
}
