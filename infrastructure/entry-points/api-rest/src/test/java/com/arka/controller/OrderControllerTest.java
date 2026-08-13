package com.arka.controller;

import com.arka.JwtService;
import com.arka.enums.OrderStatus;
import com.arka.enums.OrderType;
import com.arka.exceptions.UnauthorizedException;
import com.arka.mappers.OrderRestMapperImpl;
import com.arka.notification.SendEmailOrderStatusChangeUseCase;
import com.arka.order.CreateOrderUseCase;
import com.arka.order.ModifyOrderUseCase;
import com.arka.order.UpdateOrderStatusUseCase;
import com.arka.order.dto.CreateOrderOut;
import com.arka.order.dto.UpdateOrderOut;
import com.arka.product.dto.ProductSummaryOut;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = OrderController.class) // drop excludeAutoConfiguration
@ActiveProfiles("test")
@Import({OrderRestMapperImpl.class,
        JwtService.class})
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @MockitoBean
    private CreateOrderUseCase createOrderUseCase;

    @MockitoBean
    private ModifyOrderUseCase modifyOrderUseCase;

    @MockitoBean
    private UpdateOrderStatusUseCase updateOrderStatusUsecase;

    @MockitoBean
    private SendEmailOrderStatusChangeUseCase notifyChangeStatusUsecase;

    private final String OWNER_EMAIL = "john.wick@example.com";

    private ProductSummaryOut buildProductOutput(Long id) {
        return new ProductSummaryOut(
                id, "PR-TEST-001", "Test product", "Test Category");
    }

    private CreateOrderOut.Item buildCreateOrderItem(Long id, Long productId, int quantity) {

        return new CreateOrderOut.Item(
                id, buildProductOutput(productId), quantity, new BigDecimal("75.00"),
                new BigDecimal("75.00").multiply(BigDecimal.valueOf(quantity)));
    }

    private UpdateOrderOut.Item buildUpdateOrderItem(Long id, Long productId, int quantity) {
        return new UpdateOrderOut.Item(
                id, buildProductOutput(productId), quantity, new BigDecimal("75.00"),
                new BigDecimal("75.00").multiply(BigDecimal.valueOf(quantity)));
    }

    private CreateOrderOut.OrderContact buildCreateContact(Long id){
        return new CreateOrderOut.OrderContact(
                id,
                "Test",
                "Contact",
                "test.contact@example.com",
                "Test Company");
    }

    private UpdateOrderOut.OrderContact buildUpdateContact(Long id){
        return new UpdateOrderOut.OrderContact(
                id,
                "Test",
                "Contact",
                "Test Company",
                "test.contact@example.com");
    }

    @Test
    void shouldReturn403WhenCallerIsNotOrderOwner() throws Exception {
        Long orderId = 1L;

        when(modifyOrderUseCase.execute(any(), eq("intruder@example.com")))
                .thenThrow(new UnauthorizedException("You don't have access to this order"));

        Map<String, Object> item = Map.of(
                "productId", 1L,
                "quantity", 5
        );

        Map<String, Object> request = Map.of(
                "id", orderId,
                "notes", "Updated order notes",
                "items", Set.of(item)
        );

        mockMvc.perform(patch("/api/v1/orders")
                        .with(jwt().jwt(builder -> builder.subject("intruder@example.com")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldCreateOrderAndReturn201Created() throws Exception {

        // given
        Long expectedOrderId = 500L;

        CreateOrderOut mockOutput = new CreateOrderOut(
                expectedOrderId,
                "ORD-2026-001",
                OrderStatus.PENDING,
                "Test notes",
                OrderType.PURCHASE,
                new BigDecimal("75.00"),
                buildCreateContact(1L),
                Set.of(buildCreateOrderItem(1L, 1L, 5)),
                Instant.now());

        when(createOrderUseCase.execute(any(), eq(OWNER_EMAIL))).thenReturn(mockOutput);

        Map<String, Object> item = Map.of(
                "productId", 1L,
                "quantity", 5);

        Map<String, Object> request = Map.of(
                "notes", "Urgent restock order",
                "type", "PURCHASE",
                "items", Set.of(item));

        // when & then
        mockMvc.perform(post("/api/v1/orders")
                        .with(jwt().jwt(builder -> builder.subject(OWNER_EMAIL)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/v1/orders/" + expectedOrderId))
                .andExpect(jsonPath("$.id").value(expectedOrderId))
                .andExpect(jsonPath("$.type").value("PURCHASE"))
                .andExpect(jsonPath("$.contact.id").value(1L));
    }

    @Test
    void shouldReturn400BadRequestWhenItemsSetIsEmpty() throws Exception {
        // given - Payload violating @NotEmpty on items
        Map<String, Object> invalidRequest = Map.of(
                "notes", "Empty order",
                "type", "PURCHASE",
                "items", List.of() // Violates @NotEmpty(message = "There must be at least one item")
        );

        // when & then
        mockMvc.perform(post("/api/v1/orders")
                        .with(jwt().jwt(builder -> builder.subject(OWNER_EMAIL)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400BadRequestWhenItemQuantityIsLessThanOne() throws Exception {

        CreateOrderOut mockOutput = new CreateOrderOut(
                1L,
                "ORD-2026-001",
                OrderStatus.PENDING,
                "Test notes",
                OrderType.PURCHASE,
                new BigDecimal("75.00"),
                buildCreateContact(1L),
                Set.of(buildCreateOrderItem(1L, 1L, 0)),
                Instant.now());

        when(createOrderUseCase.execute(any(), eq(OWNER_EMAIL))).thenReturn(mockOutput);

        // given - Item quantity is 0 (violates @Min(1))
        Map<String, Object> invalidItem = Map.of(
                "productId", 1L,
                "quantity", 0
        );

        Map<String, Object> invalidRequest = Map.of(
                "type", "PURCHASE",
                "items", Set.of(invalidItem)
        );

        // when & then
        mockMvc.perform(post("/api/v1/orders")
                        .with(jwt().jwt(builder -> builder.subject(OWNER_EMAIL)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400BadRequestWhenTypeIsMissing() throws Exception {
        // given - Missing type and companyId
        Map<String, Object> item = Map.of(
                "productId", 1L,
                "quantity", 2);

        Map<String, Object> invalidRequest = Map.of(
                "items", Set.of(item)
        );

        // when & then
        mockMvc.perform(post("/api/v1/orders")
                        .with(jwt().jwt(builder -> builder.subject(OWNER_EMAIL)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdateOrderDetailsAndReturn200() throws Exception {
        // given
        Long orderId = 1L;
        UpdateOrderOut mockOutput = new UpdateOrderOut(
                orderId,
                "ORD-2026-001",
                OrderStatus.PENDING,
                "Updated order notes",
                OrderType.PURCHASE,
                Instant.now(),
                buildUpdateContact(1L),
                List.of(buildUpdateOrderItem(1L, 1L, 5))
        );

        when(modifyOrderUseCase.execute(any(), eq(OWNER_EMAIL))).thenReturn(mockOutput);

        Map<String, Object> item = Map.of(
                "productId", 1L,
                "quantity", 5
        );

        Map<String, Object> request = Map.of(
                "id", orderId,
                "notes", "Updated order notes",
                "items", Set.of(item)
        );

        // when & then
        mockMvc.perform(patch("/api/v1/orders")
                        .with(jwt().jwt(builder -> builder.subject(OWNER_EMAIL)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderId))
                .andExpect(jsonPath("$.notes").value("Updated order notes"));
    }

    @Test
    void shouldReturn400BadRequestWhenUpdateOrderPayloadIsInvalid() throws Exception {
        // given - Empty payload violating @Valid constraints on UpdateOrderRequest
        Map<String, Object> invalidRequest = Map.of();

        // when & then
        mockMvc.perform(patch("/api/v1/orders")
                        .with(jwt().jwt(builder -> builder.subject(OWNER_EMAIL)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdateOrderStatusAndNotifyUser() throws Exception {
        // given
        Long orderId = 1L;
        String userEmail = "admin@arka.com";

        UpdateOrderOut mockOutput = new UpdateOrderOut(
                orderId,
                "ORD-2026-001",
                OrderStatus.AUTHORIZED,
                "Updated order notes",
                OrderType.PURCHASE,
                Instant.now(),
                buildUpdateContact(1L),
                List.of(buildUpdateOrderItem(1L, 1L, 5))
        );

        when(updateOrderStatusUsecase.execute(eq(orderId), eq(OrderStatus.AUTHORIZED)))
                .thenReturn(mockOutput);

        Map<String, Object> request = Map.of("status", "AUTHORIZED");

        // when & then
        mockMvc.perform(patch("/api/v1/orders/{orderId}/status", orderId)
                        .with(jwt().jwt(builder -> builder.subject(OWNER_EMAIL)))
                        .requestAttr("userEmail", userEmail)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderId))
                .andExpect(jsonPath("$.status").value("AUTHORIZED"));

        // Verifies status update and email notification were both called
        verify(updateOrderStatusUsecase).execute(eq(orderId), eq(OrderStatus.AUTHORIZED));
        verify(notifyChangeStatusUsecase).execute(eq(userEmail), any());
    }

    @Test
    void shouldHandleNullUserEmailAttributeWhenUpdatingStatus() throws Exception {
        // given
        Long orderId = 1L;
        UpdateOrderOut mockOutput = new UpdateOrderOut(
                orderId,
                "ORD-2026-001",
                OrderStatus.CANCELLED,
                "Updated order notes",
                OrderType.PURCHASE,
                Instant.now(),
                buildUpdateContact(1L),
                List.of(buildUpdateOrderItem(1L, 1L, 5))
        );

        when(updateOrderStatusUsecase.execute(eq(orderId), eq(OrderStatus.CANCELLED)))
                .thenReturn(mockOutput);

        Map<String, Object> request = Map.of("status", "CANCELLED");

        // when & then - Request without setting "userEmail" attribute (passes null to notify use case)
        mockMvc.perform(patch("/api/v1/orders/{orderId}/status", orderId)
                        .with(jwt().jwt(builder -> builder.subject(OWNER_EMAIL)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CANCELLED"));

        verify(notifyChangeStatusUsecase).execute(eq(null), any());
    }
}



