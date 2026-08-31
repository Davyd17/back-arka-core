package com.arka.notification;

import com.arka.notification.dto.EmailAttachment;
import com.arka.notification.dto.EmailMessage;
import com.arka.notification.gateway.EmailGateway;
import com.arka.report.ExportFormat;
import com.arka.report.dto.SalesReportData;
import com.arka.report.gateway.ExportGateway;
import com.arka.report.service.SalesReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SendWeeklySalesReportUseCaseTest {

    @Mock
    private SalesReportService salesReportService;
    @Mock
    private ExportGateway exportGateway;
    @Mock
    private EmailGateway emailGateway;

    @InjectMocks
    private SendWeeklySalesReportUseCase useCase;

    private EmailMessage email;

    @BeforeEach
    void setUp() {
        email = new EmailMessage(
                "sender@arka.com", "recipient@arka.com", "Subject", "Body");
    }

    @Test
    void shouldSendEmailWithAttachmentWhenInputIsValid() {

        SalesReportData data = mock(SalesReportData.class);
        byte[] exported = new byte[]{1, 2, 3};

        when(salesReportService.getWeekSalesReport()).thenReturn(data);
        when(exportGateway.export(data, ExportFormat.CSV)).thenReturn(exported);

        useCase.execute(email, ExportFormat.CSV);

        verify(emailGateway).send(eq(email), any(EmailAttachment.class));
    }

    @Test
    void shouldThrowWhenEmailIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(null, ExportFormat.CSV));

        verifyNoInteractions(emailGateway);
    }

    @Test
    void shouldThrowWhenFormatIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(email, null));

        verifyNoInteractions(emailGateway);
    }
}

