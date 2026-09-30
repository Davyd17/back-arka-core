package com.arka.ses;

import com.arka.exceptions.EmailDeliveryException;
import com.arka.notification.gateway.EmailGateway;
import com.arka.notification.dto.EmailAttachment;
import com.arka.notification.dto.EmailMessage;
import com.arka.ses.factory.EmailStrategyFactory;
import jakarta.activation.DataHandler;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.RawMessage;
import software.amazon.awssdk.services.ses.model.SendRawEmailRequest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

@RequiredArgsConstructor
@Component
public class SESEmailAdapter implements EmailGateway {

    private static final Logger log =
            LoggerFactory.getLogger(SESEmailAdapter.class);

    private final SesClient client;
    private final EmailStrategyFactory strategyFactory;

    @Override
    public void send(EmailMessage email) {
        sendRaw(from(email));
    }

    private SesEmailMessage from(EmailMessage emailMessage){
        return strategyFactory
                .getEmailContentFor(emailMessage.getSubject())
                .format(emailMessage);
    }

    @SuppressWarnings("DataFlowIssue")
    private void sendRaw(SesEmailMessage sesEmail){

        try{

            Session session = Session.getDefaultInstance(new Properties());

            MimeMessage message = new MimeMessage(session);
            MimeMultipart multipart = new MimeMultipart("mixed");

            setEmailHeaders(message, sesEmail);
            addEmailTextBody(sesEmail.getBody(), multipart);

            if(sesEmail.hasAttachment())
                addEmailAttachment(sesEmail.getAttachment(), multipart);

            message.setContent(multipart);

            sendEmail(mimeToRawMessage(message));

        } catch (Exception e) {
            log.error("SES error sending to {}: {}", sesEmail.getRecipient(), e.getMessage());
            throw new EmailDeliveryException("Failed to send email via AWS SES", e);
        }
    }

    private void setEmailHeaders(MimeMessage message,
                                 SesEmailMessage sesEmail) throws MessagingException {

        message.setSubject(sesEmail.getSubject(), "UTF-8");
        message.setFrom(new InternetAddress(sesEmail.getSender()));
        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(sesEmail.getRecipient()));
    }

    private void addEmailTextBody(String textBody, MimeMultipart multiPart)
            throws MessagingException {

        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setContent(textBody, "text/html; charset=UTF-8");
        multiPart.addBodyPart(textPart);
    }

    private void addEmailAttachment(EmailAttachment attachment,
                                    MimeMultipart multipart)
            throws MessagingException {

        MimeBodyPart attachmentPart = new MimeBodyPart();

        attachmentPart.setDataHandler(new DataHandler(
                attachment.data(), attachment.format().getMimeType()));

        attachmentPart.setFileName(attachment.attachmentName());

        multipart.addBodyPart(attachmentPart);
    }

    private RawMessage mimeToRawMessage(MimeMessage message)
            throws MessagingException, IOException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        message.writeTo(outputStream);

        SdkBytes data = SdkBytes.fromByteArray(outputStream.toByteArray());
        return RawMessage.builder().data(data).build();
    }

    private void sendEmail(RawMessage rawMessage){

        SendRawEmailRequest rawEmailRequest =
                SendRawEmailRequest.builder()
                        .rawMessage(rawMessage).build();

        client.sendRawEmail(rawEmailRequest);
        log.info("Email sent successfully via SES");
    }
}
