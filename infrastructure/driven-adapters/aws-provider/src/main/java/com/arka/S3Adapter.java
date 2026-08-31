package com.arka;

import com.arka.notification.gateway.TemplateStorageGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

@RequiredArgsConstructor
@Component
public class S3Adapter implements TemplateStorageGateway {

    private final S3Client s3Client;

    @Value("${cloud-provider.aws.s3.bucket-name}")
    private String bucketName;

    @Value("${cloud-provider.aws.s3.templates.html-template-order-state-email}")
    private String orderStateEmailTemplate;

    @Override
    public String getHTMLTemplateEmailOrderStatus() {

        ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(
                getTemplate(bucketName, orderStateEmailTemplate));

        return objectBytes.asUtf8String();
    }

    private GetObjectRequest getTemplate(String bucketName, String resourceKey){
        return GetObjectRequest.builder()
                .bucket(bucketName)
                .key(resourceKey)
                .build();
    }
}
