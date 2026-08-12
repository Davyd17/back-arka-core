package com.arka.controller;

import com.arka.JwtService;
import com.arka.cart.AddItemToShoppingCartUseCase;
import com.arka.cart.ListAbandonedShoppingCartsUseCase;
import com.arka.cart.dto.AddItemShoppingCartIn;
import com.arka.cart.dto.ShoppingCartOut;
import com.arka.enums.ShoppingCartStatus;
import com.arka.mappers.ProductRestMapperImpl;
import com.arka.mappers.ShoppingCartItemRestMapperImpl;
import com.arka.mappers.ShoppingCartRestMapperImpl;
import com.arka.request.AddItemShoppingCartRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ShoppingCartController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@Import({ShoppingCartRestMapperImpl.class,
        ShoppingCartItemRestMapperImpl.class,
        ProductRestMapperImpl.class})
class ShoppingCartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private AddItemToShoppingCartUseCase addItemToShoppingCartUseCase;

    @MockitoBean
    private ListAbandonedShoppingCartsUseCase listAbandonedShoppingCartsUseCase;

    @Test
    void shouldAddItemToCartAndReturn200Ok() throws Exception {
        // given
        Long userId = 100L;
        Long productId = 50L;
        int quantity = 2;

        ShoppingCartOut mockCartOutput = new ShoppingCartOut(
                1L,
                ShoppingCartStatus.ACTIVE,
                new BigDecimal("120.00"),
                List.of(),
                Instant.now(),
                userId
        );

        when(addItemToShoppingCartUseCase.execute(any(AddItemShoppingCartIn.class)))
                .thenReturn(mockCartOutput);

        AddItemShoppingCartRequest request = new AddItemShoppingCartRequest(productId, quantity);

        // when & then
        mockMvc.perform(post("/api/v1/shopping-carts/{userId}/items", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.userId").value(userId))
                .andExpect(jsonPath("$.status").value("ACTIVE"))
                .andExpect(jsonPath("$.totalAmount").value(120.00));

        verify(addItemToShoppingCartUseCase).execute(new AddItemShoppingCartIn(userId, productId, quantity));
    }

    @Test
    void shouldReturn400BadRequestWhenQuantityIsZeroOrNegative() throws Exception {
        // given
        Long userId = 100L;
        Map<String, Object> invalidRequest = Map.of(
                "productId", 50L,
                "quantity", 0 // Violates @Min(1)
        );

        // when & then
        mockMvc.perform(post("/api/v1/shopping-carts/{userId}/items", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400BadRequestWhenProductIdIsMissing() throws Exception {
        // given
        Long userId = 100L;
        Map<String, Object> invalidRequest = Map.of(
                "quantity", 3 // Missing productId
        );

        // when & then
        mockMvc.perform(post("/api/v1/shopping-carts/{userId}/items", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

}
