package com.arka.notification;

import com.arka.notification.dto.EmailMessage;
import com.arka.notification.dto.MessageSubject;
import com.arka.notification.dto.OrderStatusEmailCommand;
import com.arka.notification.gateway.EmailGateway;
import com.arka.notification.gateway.TemplateStorageGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SendOrderStatusUpdatedEmailUseCase {

    private final TemplateStorageGateway templateStorageGateway;
    private final EmailGateway emailGateway;

    public void execute(OrderStatusEmailCommand command){

        EmailMessage email = new EmailMessage(
                command.recipient(),
                MessageSubject.ORDER_STATUS_UPDATED).toBuilder()
                .subjectArgs(new Object[]{command.orderNumber(), command.status()})
                .body(buildEmailBody(command))
                .build();

        emailGateway.send(email);
    }

    private String buildEmailBody(OrderStatusEmailCommand command){

        String htmlTemplate = templateStorageGateway
                .getHTMLTemplateEmailOrderStatus();

        return replaceHtmlPlaceHolders(htmlTemplate, command);
    }

    private String replaceHtmlPlaceHolders(String htmlTemplate, OrderStatusEmailCommand command){

        return htmlTemplate
                .replace("{{orderId}}", command.orderNumber())
                .replace("{{newStatus}}", command.status().toString())
                .replace("{{customerName}}", command.companyName())
                //TODO: Change the statusDescription: status, for an actual status description
                .replace("{{statusDescription}}", command.status().toString());
    }
}
