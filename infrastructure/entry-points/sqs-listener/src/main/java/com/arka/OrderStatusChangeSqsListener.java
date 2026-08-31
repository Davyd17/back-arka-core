package com.arka;

import com.arka.notification.SendEmailOrderStatusChangeUseCase;
import com.arka.notification.dto.OrderEmailDataIn;
import com.arka.notification.dto.OrderStatusChangeRequestedEvent;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderStatusChangeSqsListener {

    private final SendEmailOrderStatusChangeUseCase
            sendEmailOrderStatusChangeUseCase;

    @SqsListener("${cloud-provider.aws.sqs.events-queue}")
    public void handleOrderStatusChangeEvent(OrderStatusChangeRequestedEvent event){

        log.info("Received OrderStatusChangeRequestEvent from SQS for email: {}", event.recipient());
        sendEmailOrderStatusChangeUseCase.execute(event.recipient(), buildOrderEmailData(event));
        log.info("Successfully processed order status change email task for: {}", event.recipient());
    }

    private OrderEmailDataIn buildOrderEmailData(OrderStatusChangeRequestedEvent event){
        return new OrderEmailDataIn(
                event.orderNumber(),
                event.status(),
                event.companyName());
    }
}
