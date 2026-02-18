package com.arka.controller;

import com.arka.dto.out.CreateOrderOut;
import com.arka.dto.out.UpdateOrderOut;
import com.arka.mappers.request.OrderRequestMapper;
import com.arka.mappers.response.OrderResponseMapper;
import com.arka.request.CreateOrderRequest;
import com.arka.request.UpdateOrderRequest;
import com.arka.response.update.UpdateOrderResponse;
import com.arka.response.save.CreateOrderResponse;
import com.arka.usecase.GenerateOrderUseCase;
import com.arka.usecase.ModifyOrderUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(path = "api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final GenerateOrderUseCase createOrderUseCase;
    private final ModifyOrderUseCase modifyOrderUseCase;

    private final OrderRequestMapper requestMapper;
    private final OrderResponseMapper responseMapper;

    @PostMapping
    public ResponseEntity<CreateOrderResponse> create(@Valid @RequestBody  CreateOrderRequest request){

        CreateOrderOut createOrderOut =
                createOrderUseCase.execute(
                        requestMapper.toDomain(request));

        URI uri = URI.create(Long.toString(createOrderOut.id()));

        return ResponseEntity.created(uri).body(
                responseMapper.toResponse(createOrderOut));

    }

    @PatchMapping
    public ResponseEntity<UpdateOrderResponse> update(@Valid @RequestBody UpdateOrderRequest request){

        UpdateOrderOut updatedOrder = modifyOrderUseCase.execute(
                requestMapper.toDomain(request));

        return ResponseEntity.ok(responseMapper.toResponse(updatedOrder));
    }


}
