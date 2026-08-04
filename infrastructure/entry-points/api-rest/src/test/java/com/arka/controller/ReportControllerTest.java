package com.arka.controller;

import com.arka.exceptions.GlobalExceptionHandler;
import com.arka.report.ExportFormat;
import com.arka.report.GenerateLowStockReportUseCase;
import com.arka.report.GenerateSalesReportUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ReportController.class,
        excludeAutoConfiguration = {
                SecurityAutoConfiguration.class,
                SecurityFilterAutoConfiguration.class
        })
@ActiveProfiles("test")
class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GenerateSalesReportUseCase generateSalesReportUseCase;

    @MockitoBean
    private GenerateLowStockReportUseCase generateLowStockReportUseCase;

    @Test
    void shouldGenerateLowStockReportWithDefaultParameters() throws Exception {
        // given
        Long warehouseId = 10L;
        byte[] fakeFileContent = "id,product,stock\n1,Mouse,5".getBytes();

        when(generateLowStockReportUseCase.execute(eq(warehouseId), eq(40), eq(ExportFormat.CSV)))
                .thenReturn(fakeFileContent);

        // when & then
        mockMvc.perform(get("/api/v1/reports/warehouse/{warehouseInventoryId}/low-stock", warehouseId))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=low-stock-report.csv"))
                .andExpect(content().contentType(ExportFormat.CSV.getMimeType()))
                .andExpect(content().bytes(fakeFileContent));

        verify(generateLowStockReportUseCase).execute(eq(warehouseId), eq(40), eq(ExportFormat.CSV));
    }

    @Test
    void shouldGenerateLowStockReportWithCustomThreshold() throws Exception {
        // given
        Long warehouseId = 10L;
        int customThreshold = 15;
        byte[] fakeFileContent = "id,product,stock\n1,Mouse,5".getBytes();

        when(generateLowStockReportUseCase.execute(eq(warehouseId), eq(customThreshold), eq(ExportFormat.CSV)))
                .thenReturn(fakeFileContent);

        // when & then
        mockMvc.perform(get("/api/v1/reports/warehouse/{warehouseInventoryId}/low-stock", warehouseId)
                        .param("threshold", String.valueOf(customThreshold)))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=low-stock-report.csv"))
                .andExpect(content().contentType(ExportFormat.CSV.getMimeType()))
                .andExpect(content().bytes(fakeFileContent));

        verify(generateLowStockReportUseCase).execute(eq(warehouseId), eq(customThreshold), eq(ExportFormat.CSV));
    }

    @Test
    void shouldGenerateSevenDaysSalesReportWithDefaultCsvFormat() throws Exception {
        // given
        byte[] fakeCsvBytes = "order_id,total\n100,150.00".getBytes();

        when(generateSalesReportUseCase.execute(eq(ExportFormat.CSV)))
                .thenReturn(fakeCsvBytes);

        // when & then
        mockMvc.perform(get("/api/v1/reports/sales/seven-days"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=sales-report." + ExportFormat.CSV.getFileExtension()))
                .andExpect(content().contentType(ExportFormat.CSV.getMimeType()))
                .andExpect(content().bytes(fakeCsvBytes));

        verify(generateSalesReportUseCase).execute(eq(ExportFormat.CSV));
    }

    @Test
    void shouldReturn500WhenReportGenerationFails() throws Exception {
        // given
        doThrow(new RuntimeException("Error generating sales report file"))
                .when(generateSalesReportUseCase).execute(any());

        // when & then
        mockMvc.perform(get("/api/v1/reports/sales/seven-days"))
                .andExpect(status().isInternalServerError());
    }
}
