package com.arka.controller;

import com.arka.mappers.OrderRestMapper;
import com.arka.order.CreateOrderUseCase;
import com.arka.order.dto.CreateOrderOut;
import com.arka.order.dto.UpdateOrderOut;
import com.arka.request.CreateOrderRequest;
import com.arka.request.UpdateOrderRequest;
import com.arka.response.update.UpdateOrderResponse;
import com.arka.response.save.CreateOrderResponse;
import com.arka.order.ModifyOrderUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(path = "api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final ModifyOrderUseCase modifyOrderUseCase;

    private final OrderRestMapper mapper;

    @PostMapping
    public ResponseEntity<CreateOrderResponse> create(@Valid @RequestBody  CreateOrderRequest request){

        CreateOrderOut createOrderOut =
                createOrderUseCase.execute(
                        mapper.toDomain(request));

        URI uri = URI.create(Long.toString(createOrderOut.id()));

        return ResponseEntity.created(uri).body(
                mapper.toResponse(createOrderOut));

    }

    @PatchMapping
    public ResponseEntity<UpdateOrderResponse> update(@Valid @RequestBody UpdateOrderRequest request){

        UpdateOrderOut updatedOrder = modifyOrderUseCase.execute(
                mapper.toDomain(request));

        return ResponseEntity.ok(mapper.toResponse(updatedOrder));
    }


}
