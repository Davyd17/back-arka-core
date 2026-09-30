package com.arka.notification.gateway;

import com.arka.notification.dto.EmailMessage;

public interface EmailGateway {

    void send(EmailMessage email);
}
