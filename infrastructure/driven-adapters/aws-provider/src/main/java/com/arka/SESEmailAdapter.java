package com.arka;

import com.arka.notification.EmailGateway;
import com.arka.notification.dto.EmailAttachment;
import com.arka.notification.dto.EmailMessage;
import jakarta.activation.DataHandler;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.RawMessage;
import software.amazon.awssdk.services.ses.model.SendRawEmailRequest;

import java.io.ByteArrayOutputStream;
import java.util.Properties;

@RequiredArgsConstructor
@Component
public class SESEmailAdapter implements EmailGateway {

    private final SesClient client;

    @Override
    public void send(EmailMessage input,
                     EmailAttachment attachment) {

        try {
            Session session = Session.getDefaultInstance(new Properties());

            MimeMessage message = new MimeMessage(session);

            message.setSubject(input.subject(), "UTF-8");
            message.setFrom(new InternetAddress(input.sender()));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(input.recipient()));

            MimeMultipart multipart = new MimeMultipart("mixed");

            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setContent(input.body(), "text/html; charset=UTF-8");
            multipart.addBodyPart(textPart);

            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.setDataHandler(new DataHandler(
                    attachment.data(), attachment.format().getMimeType()));
            attachmentPart.setFileName(attachment.attachmentName());
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            message.writeTo(outputStream);

            SdkBytes data = SdkBytes.fromByteArray(outputStream.toByteArray());
            RawMessage rawMessage = RawMessage.builder().data(data).build();

            SendRawEmailRequest rawEmailRequest =
                    SendRawEmailRequest.builder()
                            .rawMessage(rawMessage).build();

            client.sendRawEmail(rawEmailRequest);
            System.out.println("Raw Email with attachment sent successfully via SES.");

        } catch (Exception e) {
            System.err.println("SES error: " + e.getMessage());
            throw new RuntimeException("Failed to send email via AWS SES", e);
        }
    }
}
