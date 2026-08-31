package com.arka.notification;

import com.arka.enums.OrderStatus;
import com.arka.notification.dto.EmailMessage;
import com.arka.notification.gateway.EmailGateway;
import com.arka.notification.gateway.TemplateStorageGateway;
import com.arka.notification.dto.OrderEmailDataIn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SendEmailOrderStatusChangeUseCaseTest {

    @Mock
    private TemplateStorageGateway templateStorageGateway;

    @Mock
    private EmailGateway emailGateway;

    @InjectMocks
    private SendEmailOrderStatusChangeUseCase usecase;

    private static final String TEMPLATE =
            "<p>{{orderId}} {{newStatus}} {{customerName}} {{statusDescription}}</p>";

    @BeforeEach
    void setUp(){
        ReflectionTestUtils.setField(usecase, "emailSender", "test@arka.com");

    }

    @Test
    void shouldSendEmailWithCorrectRecipientAndSubject(){

        OrderEmailDataIn input = new OrderEmailDataIn(
                "ORD-001", OrderStatus.PROCESSING, "Test");

        when(templateStorageGateway.getHTMLTemplateEmailOrderStatus())
                .thenReturn(TEMPLATE);

        usecase.execute("customer@test.com", input);

        ArgumentCaptor<EmailMessage> captor = ArgumentCaptor.forClass(EmailMessage.class);
        verify(emailGateway).send(captor.capture());

        EmailMessage sent = captor.getValue();

        assertEquals("customer@test.com", sent.recipient());
        assertEquals("Order: ORD-001, Status update: PROCESSING", sent.subject());

    }
}
