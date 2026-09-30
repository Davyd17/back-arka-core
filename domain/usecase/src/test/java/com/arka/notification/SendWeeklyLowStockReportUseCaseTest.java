package com.arka.notification;

import com.arka.notification.dto.EmailMessage;
import com.arka.notification.dto.MessageSubject;
import com.arka.notification.gateway.EmailGateway;
import com.arka.report.ExportFormat;
import com.arka.report.dto.LowStockReportCommand;
import com.arka.report.dto.LowStockReportData;
import com.arka.report.gateway.ExportGateway;
import com.arka.report.service.StockDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class SendWeeklyLowStockReportUseCaseTest {

    private ExportGateway exportGateway;
    private EmailGateway emailGateway;
    private StockDataService stockDataService;

    private SendWeeklyLowStockReportUseCase useCase;

    @BeforeEach
    void setUp() {
        exportGateway = mock(ExportGateway.class);
        emailGateway = mock(EmailGateway.class);
        stockDataService = mock(StockDataService.class);

        useCase = new SendWeeklyLowStockReportUseCase(
                exportGateway,
                emailGateway,
                stockDataService
        );
    }

    @Test
    void shouldGenerateAndSendLowStockReportWithAttachment() {
        // given
        LowStockReportCommand command = new LowStockReportCommand(
                "inventory@arka.com",
                ExportFormat.CSV,
                1L,
                5
        );

        LowStockReportData data = mock(LowStockReportData.class);
        byte[] exportedData = new byte[]{1, 2, 3};

        when(stockDataService.getLowStockByWarehouse(1L, 5))
                .thenReturn(data);

        when(exportGateway.export(data, ExportFormat.CSV))
                .thenReturn(exportedData);

        // when
        useCase.execute(command);

        // then
        verify(stockDataService)
                .getLowStockByWarehouse(1L, 5);

        verify(exportGateway)
                .export(data, ExportFormat.CSV);

        ArgumentCaptor<EmailMessage> emailCaptor =
                ArgumentCaptor.forClass(EmailMessage.class);

        verify(emailGateway).send(emailCaptor.capture());

        EmailMessage email = emailCaptor.getValue();

        assertThat(email.getRecipient())
                .isEqualTo("inventory@arka.com");

        assertThat(email.getSubject())
                .isEqualTo(MessageSubject.LOW_STOCK_REPORT);

        assertThat(email.hasAttachment())
                .isTrue();

        assertThat(email.getAttachment())
                .isNotNull();

        assertThat(email.getAttachment().data())
                .isEqualTo(exportedData);

        assertThat(email.getAttachment().attachmentName())
                .isEqualTo("low-stock-weekly-report");

        assertThat(email.getAttachment().format())
                .isEqualTo(ExportFormat.CSV);
    }

    @Test
    void shouldUseCommandRecipientWhenSendingEmail() {
        // given
        LowStockReportCommand command = new LowStockReportCommand(
                "reports@customer.com",
                ExportFormat.CSV,
                10L,
                20
        );

        LowStockReportData data = mock(LowStockReportData.class);
        byte[] exportedData = new byte[]{10, 20, 30};

        when(stockDataService.getLowStockByWarehouse(10L, 20))
                .thenReturn(data);

        when(exportGateway.export(data, ExportFormat.CSV))
                .thenReturn(exportedData);

        // when
        useCase.execute(command);

        // then
        ArgumentCaptor<EmailMessage> emailCaptor =
                ArgumentCaptor.forClass(EmailMessage.class);

        verify(emailGateway).send(emailCaptor.capture());

        assertThat(emailCaptor.getValue().getRecipient())
                .isEqualTo("reports@customer.com");
    }

    @Test
    void shouldUseCommandFormatForExport() {
        // given
        LowStockReportCommand command = new LowStockReportCommand(
                "inventory@arka.com",
                ExportFormat.CSV,
                5L,
                40
        );

        LowStockReportData data = mock(LowStockReportData.class);
        byte[] exportedData = new byte[]{1, 2, 3};

        when(stockDataService.getLowStockByWarehouse(5L, 40))
                .thenReturn(data);

        when(exportGateway.export(data, ExportFormat.CSV))
                .thenReturn(exportedData);

        // when
        useCase.execute(command);

        // then
        verify(exportGateway)
                .export(data, ExportFormat.CSV);
    }

    @Test
    void shouldUseCommandWarehouseAndThresholdWhenGettingStockData() {
        // given
        Long warehouseId = 15L;
        int threshold = 25;

        LowStockReportCommand command = new LowStockReportCommand(
                "inventory@arka.com",
                ExportFormat.CSV,
                warehouseId,
                threshold
        );

        LowStockReportData data = mock(LowStockReportData.class);
        byte[] exportedData = new byte[]{1, 2, 3};

        when(stockDataService.getLowStockByWarehouse(warehouseId, threshold))
                .thenReturn(data);

        when(exportGateway.export(data, ExportFormat.CSV))
                .thenReturn(exportedData);

        // when
        useCase.execute(command);

        // then
        verify(stockDataService)
                .getLowStockByWarehouse(warehouseId, threshold);
    }

    @Test
    void shouldThrowWhenCommandIsNull() {
        // when & then
        assertThatThrownBy(() ->
                useCase.execute(null))
                .isInstanceOf(IllegalArgumentException.class);

        verifyNoInteractions(
                stockDataService,
                exportGateway,
                emailGateway
        );
    }

    @Test
    void shouldThrowWhenAttachmentFormatIsNull() {
        // given
        LowStockReportCommand command = new LowStockReportCommand(
                "inventory@arka.com",
                null,
                1L,
                5
        );

        // when & then
        assertThatThrownBy(() ->
                useCase.execute(command))
                .isInstanceOf(IllegalArgumentException.class);

        verifyNoInteractions(
                stockDataService,
                exportGateway,
                emailGateway
        );
    }
}
