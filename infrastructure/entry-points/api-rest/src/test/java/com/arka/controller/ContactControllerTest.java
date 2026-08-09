package com.arka.controller;

import com.arka.mappers.ContactRestMapper;
import com.arka.party.CreateContactUseCase;
import com.arka.party.dto.ContactOutput;
import com.arka.party.dto.CreateContactInput;
import com.arka.request.CreateContactRequest;
import com.arka.response.get.ContactResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = InternalRestController.class,
        excludeAutoConfiguration = {
                SecurityAutoConfiguration.class,
                UserDetailsServiceAutoConfiguration.class})
class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CreateContactUseCase createContactUseCase;

    @MockitoBean
    private ContactRestMapper contactRestMapper;

    @Test
    void shouldCreateContactSuccessfully() throws Exception {
        // given
        CreateContactRequest request = new CreateContactRequest(
                "John", "Doe", "john.doe@arka.com");

        CreateContactInput input = new CreateContactInput(
                "John", "Doe", "john.doe@arka.com");

        ContactOutput output = new ContactOutput(1L,
                "John", "Doe", "john.doe@arka.com");

        ContactResponse response = new ContactResponse(
                1L, "John", "Doe", "john.doe@arka.com");

        when(contactRestMapper.toCreateInput(any(CreateContactRequest.class))).thenReturn(input);
        when(createContactUseCase.execute(input)).thenReturn(output);
        when(contactRestMapper.toResponse(output)).thenReturn(response);

        // when & then
        mockMvc.perform(post("/api/v1/internal/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value("john.doe@arka.com"));

        verify(createContactUseCase).execute(input);
    }

    @Test
    void shouldReturn400BadRequestWhenRequestBodyIsInvalid() throws Exception {
        // given: invalid payload (e.g., empty request body triggering validation error)
        String invalidJsonPayload = "{}";

        // when & then
        mockMvc.perform(post("/api/v1/internal/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJsonPayload))
                .andExpect(status().isBadRequest());
    }

}
