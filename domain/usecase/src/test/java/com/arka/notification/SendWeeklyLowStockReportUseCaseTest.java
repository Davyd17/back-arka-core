package com.arka.notification;

import com.arka.notification.dto.EmailAttachment;
import com.arka.notification.dto.EmailMessage;
import com.arka.report.ExportFormat;
import com.arka.report.dto.LowStockReportData;
import com.arka.report.gateway.ExportGateway;
import com.arka.report.service.StockDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SendWeeklyLowStockReportUseCaseTest {

    @Mock
    private ExportGateway exportGateway;

    @Mock
    private EmailGateway emailGateway;

    @Mock
    private StockDataService stockDataService;

    @InjectMocks
    private SendWeeklyLowStockReportUseCase useCase;

    private EmailMessage email;

    @BeforeEach
    void setUp() {

        email = new EmailMessage(
                "sender@arka.com", "recipient@arka.com", "Subject", "Body");
    }

    @Test
    void shouldSendEmailWithAttachmentWhenInputIsValid() {
        
        LowStockReportData data = mock(LowStockReportData.class);
        byte[] exported = new byte[]{1, 2, 3};

        when(stockDataService.getLowStockByWarehouse(1L, 5)).thenReturn(data);
        when(exportGateway.export(data, ExportFormat.CSV)).thenReturn(exported);

        useCase.execute(email, ExportFormat.CSV, 1L, 5);

        verify(emailGateway).send(eq(email), any(EmailAttachment.class));
    }

    @Test
    void shouldThrowWhenEmailIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(null, ExportFormat.CSV, 1L, 5));

        verifyNoInteractions(emailGateway);
    }

    @Test
    void shouldThrowWhenFormatIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(email, null, 1L, 5));

        verifyNoInteractions(emailGateway);
    }
}