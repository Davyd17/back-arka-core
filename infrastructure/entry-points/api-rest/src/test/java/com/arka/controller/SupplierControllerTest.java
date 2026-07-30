package com.arka.controller;

import com.arka.enums.CompanyRelationType;
import com.arka.exceptions.GlobalExceptionHandler;
import com.arka.mappers.CompanyRestMapperImpl;
import com.arka.mappers.ContactRestMapperImpl;
import com.arka.party.CreateSupplierUseCase;
import com.arka.party.ListSuppliersByCategoryUseCase;
import com.arka.party.dto.CompanyOut;
import com.arka.request.CreateCompanyRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = SupplierController.class,
        excludeAutoConfiguration = {
                SecurityAutoConfiguration.class,
                SecurityFilterAutoConfiguration.class
        })
@ActiveProfiles("test")
@Import({CompanyRestMapperImpl.class,
        GlobalExceptionHandler.class,
        ContactRestMapperImpl.class})
class SupplierControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CreateSupplierUseCase createSupplierUseCase;

    @MockitoBean
    private ListSuppliersByCategoryUseCase listSuppliersByCategoryUseCase;

    @Test
    void shouldCreateCompanyAndReturn201Created() throws Exception {
        // given
        Long expectedCompanyId = 50L;
        CompanyOut mockCompanyOutput = new CompanyOut(
                expectedCompanyId,
                "Acme Supplies Inc.",
                CompanyRelationType.SUPPLIER,
                List.of(),
                List.of()
        );

        when(createSupplierUseCase.execute(any())).thenReturn(mockCompanyOutput);

        CreateCompanyRequest request = new CreateCompanyRequest(
                "Acme Supplies Inc.",
                List.of(1L, 2L),
                List.of(10L)
        );

        // when & then
        mockMvc.perform(post("/api/v1/suppliers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string(
                        "Location",
                        "http://localhost/api/v1/suppliers/" + expectedCompanyId))
                .andExpect(jsonPath("$.id").value(expectedCompanyId))
                .andExpect(jsonPath("$.name").value("Acme Supplies Inc."));

        verify(createSupplierUseCase).execute(any());
    }

    @Test
    void shouldReturn400BadRequestWhenContactIdsListIsEmpty() throws Exception {
        // given - Violates @NotEmpty on contactIds
        Map<String, Object> invalidRequest = Map.of(
                "name", "Acme Supplies Inc.",
                "contactIds", List.of(),
                "productCategoryIds", List.of(10L)
        );

        // when & then
        mockMvc.perform(post("/api/v1/suppliers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400BadRequestWhenProductCategoryIdsListIsEmpty() throws Exception {
        // given - Violates @NotEmpty on productCategoryIds
        Map<String, Object> invalidRequest = Map.of(
                "name", "Acme Supplies Inc.",
                "contactIds", List.of(1L),
                "productCategoryIds", List.of()
        );

        // when & then
        mockMvc.perform(post("/api/v1/suppliers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn500InternalServerErrorWhenUseCaseFails() throws Exception {
        // given
        doThrow(new RuntimeException("Database error saving company"))
                .when(createSupplierUseCase).execute(any());

        CreateCompanyRequest request = new CreateCompanyRequest(
                "Acme Supplies Inc.",
                List.of(1L),
                List.of(10L)
        );

        // when & then
        mockMvc.perform(post("/api/v1/suppliers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value("UNEXPECTED_ERROR"))
                .andExpect(jsonPath("$.message").value("An unexpected error occurred"))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}
