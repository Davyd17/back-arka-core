package com.arka.mappers;

import com.arka.notification.dto.EmailMessage;
import com.arka.request.EmailMessageRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmailRestMapper {

    EmailMessage toDomain(EmailMessageRequest request);
}
