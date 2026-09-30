package com.arka.ses.factory.strategies;

import com.arka.notification.dto.EmailMessage;
import com.arka.notification.dto.MessageSubject;
import com.arka.ses.SesEmailMessage;
import com.arka.ses.factory.EmailContentStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LowStockReportEmailStrategy implements EmailContentStrategy {

    @Value("${notifications.email-settings.low-stock-report.sender}")
    private String sender;
    @Value("${notifications.email-settings.low-stock-report.subject}")
    private String subjectTitle;

    @Override
    public MessageSubject getSupportedSubject() {
        return MessageSubject.LOW_STOCK_REPORT;
    }

    @Override
    public SesEmailMessage format(EmailMessage emailMessage) {

        if(!emailMessage.hasAttachment())
            throw new IllegalArgumentException("Sales report email should have an attachment");

        String defaultBody = "Hello from Arka! This is the weekly low stock report";

        return new SesEmailMessage(
                sender,
                emailMessage.getRecipient(),
                subjectTitle,
                (emailMessage.getBody() == null) ?
                        defaultBody : emailMessage.getBody()
        ).toBuilder()
                .attachment(emailMessage.getAttachment())
                .build();
    }
}
