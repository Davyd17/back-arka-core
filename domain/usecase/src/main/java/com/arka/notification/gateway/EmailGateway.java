package com.arka.notification.gateway;

import com.arka.notification.dto.EmailAttachment;
import com.arka.notification.dto.EmailMessage;

public interface EmailGateway {

    void send(EmailMessage email, EmailAttachment attachment);
    void send(EmailMessage email);
}
