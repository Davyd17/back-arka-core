package com.arka.controller;

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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(path = "api/v1/orders")
@RequiredArgsConstructor
@Validated
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final ModifyOrderUseCase modifyOrderUseCase;
    private final UpdateOrderStatusUseCase updateOrderStatusUsecase;
    private final SendEmailOrderStatusChangeUseCase notifyChangeStatusUsecase;

    private final OrderRestMapper mapper;

    @PostMapping
    public ResponseEntity<CreateOrderResponse> create(@Valid @RequestBody CreateOrderRequest request) {

        CreateOrderOut createOrderOut =
                createOrderUseCase.execute(
                        mapper.toDomain(request));

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createOrderOut.id())
                .toUri();

        return ResponseEntity.created(uri).body(
                mapper.toResponse(createOrderOut));

    }

    @PatchMapping
    public ResponseEntity<UpdateOrderResponse> update(@Valid @RequestBody UpdateOrderRequest request) {

        UpdateOrderOut updatedOrder = modifyOrderUseCase.execute(
                mapper.toDomain(request));

        return ResponseEntity.ok(mapper.toResponse(updatedOrder));
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<UpdateOrderResponse> updateStatus(
            @PathVariable @NotNull Long orderId,
            @RequestBody UpdateOrderStatusRequest request,
            HttpServletRequest httpRequest) {

        String userEmail = (String) httpRequest.getAttribute("userEmail");

        UpdateOrderOut updatedOrder =
                updateOrderStatusUsecase.execute(orderId, request.status());

        notifyChangeStatusUsecase.execute(userEmail, mapper.toEmailData(updatedOrder));

        return ResponseEntity.ok(mapper.toResponse(updatedOrder));
    }


}
