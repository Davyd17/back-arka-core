package com.arka.notification;

import com.arka.notification.dto.EmailMessage;
import com.arka.notification.gateway.EmailGateway;
import com.arka.report.ExportFormat;
import com.arka.report.dto.SalesReportCommand;
import com.arka.report.dto.SalesReportData;
import com.arka.report.gateway.ExportGateway;
import com.arka.report.service.SalesReportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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

    private SalesReportCommand buildCommand() {
        return new SalesReportCommand("recipient@arka.com", ExportFormat.CSV);
    }

    @Test
    void shouldSendEmailWithAttachmentWhenInputIsValid() {

        SalesReportCommand command = buildCommand();
        SalesReportData data = mock(SalesReportData.class);
        byte[] exported = new byte[]{1, 2, 3};

        when(salesReportService.getWeekSalesReport()).thenReturn(data);
        when(exportGateway.export(data, ExportFormat.CSV)).thenReturn(exported);

        useCase.execute(command);

        ArgumentCaptor<EmailMessage> captor = ArgumentCaptor.forClass(EmailMessage.class);
        verify(emailGateway).send(captor.capture());

        EmailMessage sentEmail = captor.getValue();
        assertEquals("recipient@arka.com", sentEmail.getRecipient());
        assertNotNull(sentEmail.getAttachment());
    }

    @Test
    void shouldThrowWhenRecipientIsNull() {
        SalesReportCommand command = new SalesReportCommand(null, ExportFormat.CSV);

        assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(command));

        verifyNoInteractions(emailGateway);
    }

    @Test
    void shouldThrowWhenAttachmentFormatIsNull() {
        SalesReportCommand command = new SalesReportCommand("recipient@arka.com", null);

        assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(command));

        verifyNoInteractions(emailGateway);
    }
}
