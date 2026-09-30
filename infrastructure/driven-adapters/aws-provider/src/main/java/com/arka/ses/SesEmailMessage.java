package com.arka.ses;

import com.arka.notification.dto.EmailAttachment;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@AllArgsConstructor
@Getter
public class SesEmailMessage {

    private String sender;
    private String recipient;
    private String subject;
    private String body;
    @Nullable private EmailAttachment attachment;

    public SesEmailMessage(String sender,
                           String recipient,
                           String subject,
                           String body){
        this.sender = sender;
        this.recipient = recipient;
        this.subject = subject;
        this.body = body;
    }

    public boolean hasAttachment(){
        return attachment != null;
    }
}
