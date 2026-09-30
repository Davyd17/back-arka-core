package com.arka.controller;

import com.arka.JwtService;
import com.arka.events.RequestLowStockReportUseCase;
import com.arka.events.RequestWeekSalesReportUseCase;
import com.arka.report.ExportFormat;
import com.arka.report.dto.LowStockReportCommand;
import com.arka.report.dto.SalesReportCommand;
import com.arka.request.EmailMessageRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InternalReportController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class InternalReportControllerTest {

    @MockitoBean
    private JwtService jwtService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private RequestWeekSalesReportUseCase requestWeekSalesReportUseCase;

    @MockitoBean
    private RequestLowStockReportUseCase requestLowStockReportUseCase;

    private EmailMessageRequest emailMessageRequest;

    @BeforeEach
    void setUp(){
        emailMessageRequest = new EmailMessageRequest(
                "reports@arka.com");
    }

    @Test
    void shouldTriggerWeeklySalesReportWithDefaultFormat() throws Exception {

        // when & then
        mockMvc.perform(post("/api/v1/reports/internal/sales/weekly")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(emailMessageRequest)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.code").value("REPORT_REQUESTED"))
                .andExpect(jsonPath("$.message")
                        .value("Sales report generation and email delivery have been requested"));

        // then
        verify(requestWeekSalesReportUseCase).execute(
                argThat(command ->
                        command instanceof SalesReportCommand(String recipient, ExportFormat attachmentFormat)
                                && recipient.equals("reports@arka.com")
                                && attachmentFormat == ExportFormat.CSV
                )
        );
    }

    @Test
    void shouldTriggerWeeklySalesReportWithProvidedFormat() throws Exception {
        // when & then
        mockMvc.perform(post("/api/v1/reports/internal/sales/weekly")
                        .param("format", "CSV")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(emailMessageRequest)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.code").value("REPORT_REQUESTED"));

        // then
        verify(requestWeekSalesReportUseCase).execute(
                argThat(command ->
                        command instanceof SalesReportCommand(String recipient, ExportFormat attachmentFormat)
                                && recipient.equals("reports@arka.com")
                                && attachmentFormat == ExportFormat.CSV
                )
        );
    }

    @Test
    void shouldTriggerWeeklyLowStockReportWithDefaultParameters() throws Exception {
        // given
        Long warehouseId = 5L;

        // when & then
        mockMvc.perform(post(
                        "/api/v1/reports/internal/warehouse/{warehouseId}/low-stock/weekly",
                        warehouseId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(emailMessageRequest)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.code").value("REPORT_REQUESTED"))
                .andExpect(jsonPath("$.message")
                        .value("Low stock report generation and email delivery have been requested"));

        // then
        verify(requestLowStockReportUseCase).execute(
                argThat(command ->
                        command instanceof LowStockReportCommand(
                                String recipient, ExportFormat attachmentFormat, Long id, int threshold
                        )
                                && recipient.equals("reports@arka.com")
                                && attachmentFormat == ExportFormat.CSV
                                && id.equals(warehouseId)
                                && threshold == 40
                )
        );
    }

    @Test
    void shouldTriggerWeeklyLowStockReportWithProvidedParameters() throws Exception {
        // given
        Long warehouseId = 10L;
        int threshold = 25;

        // when & then
        mockMvc.perform(post(
                        "/api/v1/reports/internal/warehouse/{warehouseId}/low-stock/weekly",
                        warehouseId)
                        .param("threshold", String.valueOf(threshold))
                        .param("format", "CSV")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(emailMessageRequest)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.code").value("REPORT_REQUESTED"));

        // then
        verify(requestLowStockReportUseCase).execute(
                argThat(command ->
                        command instanceof LowStockReportCommand(
                                String recipient, ExportFormat attachmentFormat, Long id, int threshold1
                        )
                                && recipient.equals("reports@arka.com")
                                && attachmentFormat == ExportFormat.CSV
                                && id.equals(warehouseId)
                                && threshold1 == threshold
                )
        );
    }
}
