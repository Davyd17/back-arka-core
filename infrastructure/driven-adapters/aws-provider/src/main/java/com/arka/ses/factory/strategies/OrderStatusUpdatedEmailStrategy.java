package com.arka.ses.factory.strategies;

import com.arka.notification.dto.EmailMessage;
import com.arka.notification.dto.MessageSubject;
import com.arka.ses.SesEmailMessage;
import com.arka.ses.factory.EmailContentStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OrderStatusUpdatedEmailStrategy implements EmailContentStrategy {

    @Value("${notifications.email-settings.order-status-updated.sender}")
    private String sender;

    @Override
    public MessageSubject getSupportedSubject() {
        return MessageSubject.ORDER_STATUS_UPDATED;
    }

    @Override
    public SesEmailMessage format(EmailMessage emailMessage) {
        return new SesEmailMessage(
                sender,
                emailMessage.getRecipient(),
                emailMessage.getSubject()
                        .resolve(emailMessage.getSubjectArgs()),
                emailMessage.getBody()
        );
    }
}
