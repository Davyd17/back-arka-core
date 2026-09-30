package com.arka.notification;

import com.arka.enums.OrderStatus;
import com.arka.notification.dto.EmailMessage;
import com.arka.notification.dto.MessageSubject;
import com.arka.notification.dto.OrderStatusEmailCommand;
import com.arka.notification.gateway.EmailGateway;
import com.arka.notification.gateway.TemplateStorageGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class SendOrderStatusUpdatedEmailUseCaseTest {

    private TemplateStorageGateway templateStorageGateway;
    private EmailGateway emailGateway;

    private SendOrderStatusUpdatedEmailUseCase useCase;

    @BeforeEach
    void setUp() {
        templateStorageGateway = mock(TemplateStorageGateway.class);
        emailGateway = mock(EmailGateway.class);

        useCase = new SendOrderStatusUpdatedEmailUseCase(
                templateStorageGateway,
                emailGateway
        );
    }

    @Test
    void shouldBuildAndSendOrderStatusUpdatedEmail() {
        // given
        OrderStatusEmailCommand command = new OrderStatusEmailCommand(
                "customer@arka.com",
                "ORD-123",
                OrderStatus.PROCESSING,
                "Acme Store"
        );

        String template = """
                <html>
                    <body>
                        <h1>Order {{orderId}}</h1>
                        <p>Hello {{customerName}}</p>
                        <p>Status: {{newStatus}}</p>
                        <p>{{statusDescription}}</p>
                    </body>
                </html>
                """;

        when(templateStorageGateway.getHTMLTemplateEmailOrderStatus())
                .thenReturn(template);

        // when
        useCase.execute(command);

        // then
        ArgumentCaptor<EmailMessage> emailCaptor =
                ArgumentCaptor.forClass(EmailMessage.class);

        verify(emailGateway).send(emailCaptor.capture());

        EmailMessage email = emailCaptor.getValue();

        assertThat(email.getRecipient())
                .isEqualTo("customer@arka.com");

        assertThat(email.getSubject())
                .isEqualTo(MessageSubject.ORDER_STATUS_UPDATED);

        assertThat(email.getSubjectArgs())
                .containsExactly("ORD-123", OrderStatus.PROCESSING);

        assertThat(email.getBody())
                .contains("Order ORD-123")
                .contains("Hello Acme Store")
                .contains("Status: PROCESSING")
                .contains("PROCESSING");

        assertThat(email.getBody())
                .doesNotContain("{{orderId}}")
                .doesNotContain("{{customerName}}")
                .doesNotContain("{{newStatus}}")
                .doesNotContain("{{statusDescription}}");
    }

    @Test
    void shouldRetrieveOrderStatusEmailTemplate() {
        // given
        OrderStatusEmailCommand command = new OrderStatusEmailCommand(
                "customer@arka.com",
                "ORD-456",
                OrderStatus.AUTHORIZED,
                "Tech Store"
        );

        String template = "Order {{orderId}} status: {{newStatus}}";

        when(templateStorageGateway.getHTMLTemplateEmailOrderStatus())
                .thenReturn(template);

        // when
        useCase.execute(command);

        // then
        verify(templateStorageGateway)
                .getHTMLTemplateEmailOrderStatus();
    }

    @Test
    void shouldSendEmailToCommandRecipient() {
        // given
        OrderStatusEmailCommand command = new OrderStatusEmailCommand(
                "orders@customer.com",
                "ORD-789",
                OrderStatus.CANCELLED,
                "My Store"
        );

        when(templateStorageGateway.getHTMLTemplateEmailOrderStatus())
                .thenReturn("Order {{orderId}}");

        // when
        useCase.execute(command);

        // then
        ArgumentCaptor<EmailMessage> emailCaptor =
                ArgumentCaptor.forClass(EmailMessage.class);

        verify(emailGateway).send(emailCaptor.capture());

        assertThat(emailCaptor.getValue().getRecipient())
                .isEqualTo("orders@customer.com");
    }

    @Test
    void shouldReplaceAllOrderStatusPlaceholders() {
        // given
        OrderStatusEmailCommand command = new OrderStatusEmailCommand(
                "customer@arka.com",
                "ARKA-001",
                OrderStatus.AUTHORIZED,
                "Colombian Tech Store"
        );

        String template = """
                Order: {{orderId}}
                Customer: {{customerName}}
                New status: {{newStatus}}
                Description: {{statusDescription}}
                """;

        when(templateStorageGateway.getHTMLTemplateEmailOrderStatus())
                .thenReturn(template);

        // when
        useCase.execute(command);

        // then
        ArgumentCaptor<EmailMessage> emailCaptor =
                ArgumentCaptor.forClass(EmailMessage.class);

        verify(emailGateway).send(emailCaptor.capture());

        assertThat(emailCaptor.getValue().getBody())
                .isEqualTo("""
                        Order: ARKA-001
                        Customer: Colombian Tech Store
                        New status: AUTHORIZED
                        Description: AUTHORIZED
                        """);
    }

    @Test
    void shouldUseOrderStatusUpdatedSubject() {
        // given
        OrderStatusEmailCommand command = new OrderStatusEmailCommand(
                "customer@arka.com",
                "ORD-999",
                OrderStatus.PROCESSING,
                "Acme Store"
        );

        when(templateStorageGateway.getHTMLTemplateEmailOrderStatus())
                .thenReturn("Order {{orderId}}");

        // when
        useCase.execute(command);

        // then
        ArgumentCaptor<EmailMessage> emailCaptor =
                ArgumentCaptor.forClass(EmailMessage.class);

        verify(emailGateway).send(emailCaptor.capture());

        EmailMessage email = emailCaptor.getValue();

        assertThat(email.getSubject())
                .isEqualTo(MessageSubject.ORDER_STATUS_UPDATED);

        assertThat(email.getSubjectArgs())
                .containsExactly(
                        "ORD-999",
                        OrderStatus.PROCESSING
                );
    }
}
