package com.arka.controller;

import com.arka.JwtService;
import com.arka.enums.ShippingStatus;
import com.arka.mappers.ShippingDetailRestMapperImpl;
import com.arka.shipping.RegisterShippingDetailsUseCase;
import com.arka.shipping.dto.ShippingDetailOut;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ShippingDetailController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@Import(ShippingDetailRestMapperImpl.class)
class ShippingDetailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private RegisterShippingDetailsUseCase registerShippingDetailsUseCase;

    @Test
    void shouldRegisterShippingDetailsAndReturn201Created() throws Exception {
        // given
        Long expectedId = 150L;
        ShippingDetailOut mockOutput = new ShippingDetailOut(
                expectedId,
                "DHL Express",
                "TRK-987654321",
                "Fragile package",
                ShippingStatus.PENDING,
                Instant.now(),
                null,
                null,
                null
        );

        when(registerShippingDetailsUseCase.execute(any())).thenReturn(mockOutput);

        Map<String, Object> request = Map.of(
                "carrier", "DHL Express",
                "trackingNumber", "TRK-987654321",
                "notes", "Fragile package",
                "orderId", 100L,
                "originAddressId", 1L,
                "destinationAddressId", 2L
        );

        // when & then
        mockMvc.perform(post("/api/v1/shipping-details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/v1/shipping-details/" + expectedId))
                .andExpect(jsonPath("$.id").value(expectedId))
                .andExpect(jsonPath("$.carrier").value("DHL Express"))
                .andExpect(jsonPath("$.trackingNumber").value("TRK-987654321"));
    }

    @Test
    void shouldReturn400BadRequestWhenRequiredFieldsAreMissing() throws Exception {
        // given - Payload missing required fields like carrier, trackingNumber, orderId
        Map<String, Object> invalidRequest = Map.of(
                "notes", "Missing required IDs and carrier"
        );

        // when & then
        mockMvc.perform(post("/api/v1/shipping-details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn500InternalServerErrorWhenUseCaseFails() throws Exception {
        // given
        doThrow(new RuntimeException("An unexpected error occurred"))
                .when(registerShippingDetailsUseCase).execute(any());

        Map<String, Object> request = Map.of(
                "carrier", "FedEx",
                "trackingNumber", "TRK-000000",
                "orderId", 100L,
                "originAddressId", 1L,
                "destinationAddressId", 2L
        );

        // when & then
        mockMvc.perform(post("/api/v1/shipping-details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value("UNEXPECTED_ERROR"))
                .andExpect(jsonPath("$.message").value("An unexpected error occurred"))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}
