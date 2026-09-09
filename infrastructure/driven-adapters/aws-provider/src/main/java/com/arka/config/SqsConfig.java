package com.arka.config;

import io.awspring.cloud.sqs.operations.SqsTemplate;
import io.awspring.cloud.sqs.support.converter.AbstractMessagingMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

/**
 * By default, SqsTemplate stamps each message with a JavaType header
 * (the producer's class name). Consumers in other services don't share
 * that class, so it can trigger a double conversion and break
 * deserialization. Disabling it forces consumers to rely only on the
 * JSON body — the actual contract between services.
 */
@Configuration
public class SqsConfig {

    @Bean
    public SqsTemplate sqsTemplate(SqsAsyncClient sqsAsyncClient){
        return SqsTemplate.builder()
                .sqsAsyncClient(sqsAsyncClient)
                .configureDefaultConverter(
                        AbstractMessagingMessageConverter::doNotSendPayloadTypeHeader)
                .build();
    }
}
