package com.arka.notification;

import com.arka.notification.dto.EmailMessage;
import com.arka.order.dto.OrderEmailDataIn;
import com.arka.order.mapper.OrderMapper;
import com.arka.util.NullValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@RequiredArgsConstructor
public class SendEmailOrderStatusChangeUseCase {

    private final TemplateStorageGateway templateStorageGateway;
    private final EmailGateway emailGateway;

    @Value("${cloud-provider.aws.ses.sender}")
    private String emailSender;

    public void execute(String recipient, OrderEmailDataIn input){

        NullValidator.validate(recipient, "recipient");
        NullValidator.validate(input, "Email input");

        String htmlTemplate = templateStorageGateway
                .getHTMLTemplateEmailOrderStatus();

        String subject = String.format(
                "Order: %s, Status update: %s", input.number(), input.status());

        String filledTemplate = replaceHtmlPlaceHolders(htmlTemplate, input);

        EmailMessage email = new EmailMessage(
                emailSender,
                recipient,
                subject,
                filledTemplate);

        emailGateway.send(email);
    }

    private String replaceHtmlPlaceHolders(String htmlTemplate, OrderEmailDataIn input){

        return htmlTemplate
                .replace("{{orderId}}", input.number())
                .replace("{{newStatus}}", input.status().toString())
                .replace("{{customerName}}", input.companyName())
                //TODO: Change the statusDescription: status, for an actual status description
                .replace("{{statusDescription}}", input.status().toString());
    }
}
