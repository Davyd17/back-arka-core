package com.arka;

import com.arka.events.mapper.OrderStatusEmailEventMapper;
import com.arka.notification.SendOrderStatusUpdatedEmailUseCase;
import com.arka.notification.dto.OrderStatusEmailCommand;
import com.arka.notification.dto.OrderStatusEmailRequestedEvent;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderStatusEmailSqsListener {

    private final OrderStatusEmailEventMapper mapper;

    private final SendOrderStatusUpdatedEmailUseCase
            sendOrderStatusUpdatedEmailUseCase;

    @SqsListener("${cloud-provider.aws.sqs.order-status-notify-queue}")
    public void handleOrderStatusChangeEvent(OrderStatusEmailRequestedEvent event){

        log.info("Received OrderStatusChangeRequestEvent from SQS for email: {}", event.recipient());
        sendOrderStatusUpdatedEmailUseCase.execute(mapper.toCommand(event));
        log.info("Successfully processed order status change email task for: {}", event.recipient());
    }
}
