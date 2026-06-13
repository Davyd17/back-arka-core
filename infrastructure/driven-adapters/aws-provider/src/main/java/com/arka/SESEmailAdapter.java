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

    @Override
    public void send(EmailMessage input,
                     EmailAttachment attachment) {

        try {

            Session session = Session.getDefaultInstance(new Properties());

            MimeMessage message = new MimeMessage(session);
            MimeMultipart multipart = new MimeMultipart("mixed");

            buildEmail(message, multipart, input, attachment);

            sendEmail(mimeToRawMessage(message));

        } catch (Exception e) {
            log.error("SES error: {}", e.getMessage());
            throw new RuntimeException("Failed to send email via AWS SES", e);
        }
    }

    private void buildEmail(MimeMessage message,
                            MimeMultipart multipart,
                            EmailMessage input,
                            EmailAttachment attachment) throws MessagingException{

        setEmailHeaders(message, input);

        addEmailTextBody(input.body(), multipart);
        addEmailAttachment(attachment, multipart);

        message.setContent(multipart);
    }

    private void setEmailHeaders(MimeMessage message,
                                 EmailMessage input) throws MessagingException {

        message.setSubject(input.subject(), "UTF-8");
        message.setFrom(new InternetAddress(input.sender()));
        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(input.recipient()));
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
