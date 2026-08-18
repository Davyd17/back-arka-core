package com.arka.controller;

import com.arka.docs.CommonApiResponses;
import com.arka.exceptions.ErrorResponse;
import com.arka.mappers.OrderRestMapper;
import com.arka.notification.SendEmailOrderStatusChangeUseCase;
import com.arka.order.CreateOrderUseCase;
import com.arka.order.UpdateOrderStatusUseCase;
import com.arka.order.dto.CreateOrderOut;
import com.arka.order.dto.UpdateOrderOut;
import com.arka.request.CreateOrderRequest;
import com.arka.request.UpdateOrderRequest;
import com.arka.request.UpdateOrderStatusRequest;
import com.arka.response.update.UpdateOrderResponse;
import com.arka.response.save.CreateOrderResponse;
import com.arka.order.ModifyOrderUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(path = "api/v1/orders")
@RequiredArgsConstructor
@Validated
@Tag(name = "Orders", description = "Order management for sales and purchase operations")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final ModifyOrderUseCase modifyOrderUseCase;
    private final UpdateOrderStatusUseCase updateOrderStatusUsecase;
    private final SendEmailOrderStatusChangeUseCase notifyChangeStatusUsecase;

    private final OrderRestMapper mapper;

    @Operation(
            summary = "Create a new order",
            description = "Creates an order attached to the authenticated user (owner)"
    )
    @CommonApiResponses
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Order created successfully",
                    content = @Content(schema = @Schema(implementation = CreateOrderResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request or validation error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))

    })
    @PostMapping
    public ResponseEntity<CreateOrderResponse> create(
            @Valid @RequestBody CreateOrderRequest request,
            Authentication authentication) {

        String email = authentication.getName();
        CreateOrderOut orderOutput =
                createOrderUseCase.execute(mapper.toDomain(request), email);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(orderOutput.id())
                .toUri();

        return ResponseEntity.created(uri).body(mapper.toResponse(orderOutput));

    }

    @Operation(
            summary = "Update specific order by ID",
            description = "Updates the specified order and validates ownership " +
                    "(only the owner of the order can update it)"
    )
    @CommonApiResponses
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order updated successfully",
                    content = @Content(schema = @Schema(implementation = CreateOrderResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request or validation error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Order not found")

    })
    @PatchMapping("/{orderId}")
    public ResponseEntity<UpdateOrderResponse> update(@Valid @RequestBody UpdateOrderRequest request,
                                                      @PathVariable @NotNull Long orderId,
                                                      Authentication authentication) {

        String email = authentication.getName();
        UpdateOrderOut updatedOrder = modifyOrderUseCase.execute(
                mapper.toDomain(request), orderId, email);

        return ResponseEntity.ok(mapper.toResponse(updatedOrder));
    }

    @Operation(
            summary = "Update order status by ID",
            description = "Update status following this transition order = " +
                    " PENDING -> PROCESSING | PROCESSING -> AUTHORIZED OR CANCELLED"
    )
    @CommonApiResponses
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order status updated successfully",
                    content = @Content(schema = @Schema(implementation = CreateOrderResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid transition or validation error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Order not found")

    })
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<UpdateOrderResponse> updateStatus(
            @PathVariable @NotNull Long orderId,
            @RequestBody UpdateOrderStatusRequest request,
            Authentication authentication) {

        String email = authentication.getName();
        UpdateOrderOut updatedOrder =
                updateOrderStatusUsecase.execute(orderId, request.status());

        notifyChangeStatusUsecase.execute(email, mapper.toEmailData(updatedOrder));

        return ResponseEntity.ok(mapper.toResponse(updatedOrder));
    }


}
