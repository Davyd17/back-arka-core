package com.arka.controller;

import com.arka.enums.InventoryMovementType;
import com.arka.inventory.RegisterInventoryMovementUseCase;
import com.arka.inventory.dto.CreateInventoryMovementOut;
import com.arka.mappers.InventoryMovementRestMapperImpl;
import com.arka.party.dto.EmployeeOut;
import com.arka.product.dto.ProductSummaryOut;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = InventoryMovementController.class,
        excludeAutoConfiguration = {
                SecurityAutoConfiguration.class,
                SecurityFilterAutoConfiguration.class
        })
@ActiveProfiles("test")
@Import({InventoryMovementRestMapperImpl.class})
class InventoryMovementControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private RegisterInventoryMovementUseCase registerInventoryMovementUseCase;

    private ProductSummaryOut buildProductOutput(Long id){
        return new ProductSummaryOut(
                id, "TEST-PROD-001", "Test Product", "Test Category");
    }

    private EmployeeOut buildEmployeeOutput(Long id){
        return new EmployeeOut(
                id, 1234, "Hernan Torres",
                "Inventory manager", "hernan@test.com");
    }

    @Test
    void shouldRegisterInventoryMovementAndReturnCreated() throws Exception {
        // given
        Long expectedId = 100L;
        CreateInventoryMovementOut mockOutput = new CreateInventoryMovementOut(
                expectedId,
                InventoryMovementType.IN,
                50,
                100,
                150,
                "Restock initial inventory",
                Instant.now(),
                buildProductOutput(10L),
                buildEmployeeOutput(1L));

        when(registerInventoryMovementUseCase.execute(any())).thenReturn(mockOutput);

        Map<String, Object> request = Map.of(
                "type", "IN",
                "quantity", 50,
                "notes", "Restock initial inventory",
                "productId", 10L,
                "employeeId", 1L,
                "warehouseId", 2L
        );

        // when & then
        mockMvc.perform(post("/api/v1/inventory-movements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/v1/inventory-movements/" + expectedId))
                .andExpect(jsonPath("$.id").value(expectedId))
                .andExpect(jsonPath("$.type").value("IN"))
                .andExpect(jsonPath("$.quantity").value(50));
    }

    @Test
    void shouldReturn400BadRequestWhenQuantityIsLessThanOne() throws Exception {
        // given - Quantity is 0 (violates @Min(1)) and missing required IDs
        Map<String, Object> invalidRequest = Map.of(
                "type", "IN",
                "quantity", 0,
                "notes", "Invalid quantity entry"
        );

        // when & then
        mockMvc.perform(post("/api/v1/inventory-movements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());

    }

    @Test
    void shouldReturn400BadRequestWhenRequiredFieldsAreMissing() throws Exception {
        // given - Missing type, productId, employeeId, warehouseId
        Map<String, Object> emptyRequest = Map.of(
                "quantity", 10
        );

        // when & then
        mockMvc.perform(post("/api/v1/inventory-movements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emptyRequest)))
                .andExpect(status().isBadRequest());
    }
}
