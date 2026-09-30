package com.arka.ses.factory;

import com.arka.notification.dto.EmailMessage;
import com.arka.notification.dto.MessageSubject;
import com.arka.ses.SesEmailMessage;


public interface EmailContentStrategy {
    MessageSubject getSupportedSubject();
    SesEmailMessage format(EmailMessage emailMessage);
}
