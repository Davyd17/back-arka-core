package com.arka.notification.dto;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@AllArgsConstructor
@Getter
public class EmailMessage {

    private String recipient;
    private MessageSubject subject;
    @Nullable private Object[] subjectArgs;
    @Nullable private EmailAttachment attachment;
    @Nullable private String body;

    public EmailMessage(String recipient, MessageSubject subject){
        this.recipient = recipient;
        this.subject = subject;
    }

    public boolean hasCustomBody(){
        return body != null;
    }

    public boolean hasAttachment(){
        return attachment != null;
    }
}
