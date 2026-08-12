package com.arka.controller;

import com.arka.JwtAuthenticationFilter;
import com.arka.JwtService;
import com.arka.config.SecurityConfig;
import com.arka.mappers.EmailRestMapperImpl;
import com.arka.notification.SendWeeklyLowStockReportUseCase;
import com.arka.notification.SendWeeklySalesReportUseCase;
import com.arka.report.ExportFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = InternalReportController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@Import({EmailRestMapperImpl.class})
class InternalReportControllerTest {

    @MockitoBean
    private JwtService jwtService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private SendWeeklySalesReportUseCase salesReportUseCase;

    @MockitoBean
    private SendWeeklyLowStockReportUseCase lowStockReportUseCase;

    @Test
    void shouldTriggerWeeklySalesReportWithDefaultFormat() throws Exception {
        // given
        Map<String, Object> request = Map.of(
                "recipientEmail", "reports@arka.com",
                "subject", "Weekly Sales"
        );

        // when & then
        mockMvc.perform(post("/api/v1/reports/internal/sales/weekly")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Weekly report generated and email sent successfully."));

        // Verifies default format CSV was applied
        verify(salesReportUseCase).execute(any(), eq(ExportFormat.CSV));
    }

    @Test
    void shouldReturn500WhenSalesReportFails() throws Exception {
        // given
        Map<String, Object> request = Map.of(
                "recipientEmail", "reports@arka.com",
                "subject", "Weekly Sales"
        );

        doThrow(new RuntimeException("Mail server unavailable"))
                .when(salesReportUseCase).execute(any(), any());

        // when & then
        mockMvc.perform(post("/api/v1/reports/internal/sales/weekly")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Failed to generate report: Mail server unavailable"));
    }

    @Test
    void shouldTriggerWeeklyLowStockReportWithDefaultParameters() throws Exception {
        // given
        Long warehouseId = 5L;
        Map<String, Object> request = Map.of(
                "recipientEmail", "inventory@arka.com",
                "subject", "Low Stock Alert"
        );

        // when & then
        mockMvc.perform(post("/api/v1/reports/internal/warehouse/{warehouseId}/low-stock/weekly", warehouseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Weekly report generated and email sent successfully."));

        // Verifies default query params: threshold = 40, format = CSV
        verify(lowStockReportUseCase).execute(any(), eq(ExportFormat.CSV), eq(warehouseId), eq(40));
    }

    @Test
    void shouldReturn500WhenLowStockReportFails() throws Exception {
        // given
        Long warehouseId = 5L;
        Map<String, Object> request = Map.of(
                "recipientEmail", "inventory@arka.com",
                "subject", "Low Stock Alert"
        );

        doThrow(new RuntimeException("Warehouse not found"))
                .when(lowStockReportUseCase).execute(any(), any(), eq(warehouseId), any(Integer.class));

        // when & then
        mockMvc.perform(post("/api/v1/reports/internal/warehouse/{warehouseId}/low-stock/weekly", warehouseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Failed to generate report: Warehouse not found"));
    }
}
