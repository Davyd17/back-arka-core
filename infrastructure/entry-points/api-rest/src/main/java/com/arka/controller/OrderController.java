package com.arka.controller;

import com.arka.dto.out.CreateOrderOut;
import com.arka.mappers.request.CreateOrderRequestMapper;
import com.arka.mappers.response.CreateOrderResponseMapper;
import com.arka.request.CreateOrderRequest;
import com.arka.response.save.CreateOrderResponse;
import com.arka.usecase.GenerateOrderUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(path = "api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final GenerateOrderUseCase createOrderUseCase;
    private final CreateOrderRequestMapper createRequestMapper;
    private final CreateOrderResponseMapper createResponseMapper;

    @PostMapping
    public ResponseEntity<CreateOrderResponse> create(@Valid @RequestBody  CreateOrderRequest request){

        CreateOrderOut createOrderOut =
                createOrderUseCase.execute(
                        createRequestMapper.toDomain(request));

        URI uri = URI.create(Long.toString(createOrderOut.id()));

        return ResponseEntity.created(uri).body(
                createResponseMapper.toResponse(createOrderOut));

    }
}
