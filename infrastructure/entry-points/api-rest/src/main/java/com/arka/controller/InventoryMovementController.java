package com.arka.controller;

import com.arka.entities.inventory.InventoryMovement;
import com.arka.mappers.request.InventoryMovementMapper;
import com.arka.request.CreateInventoryMovementRequest;
import com.arka.inventory.RegisterInventoryMovementUseCase;
import com.arka.response.save.CreateInventoryMovementResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequestMapping(path = "api/v1/inventory-movements")
@RestController
@RequiredArgsConstructor
public class InventoryMovementController {

    private final RegisterInventoryMovementUseCase registerInventoryMovementUseCase;

    private final InventoryMovementMapper mapper;

    @PostMapping
    public ResponseEntity<CreateInventoryMovementResponse> create(@Valid @RequestBody CreateInventoryMovementRequest request){

        CreateInventoryMovementResponse inventory = mapper.toCreateResponse(
                registerInventoryMovementUseCase.execute(mapper.toInput(request)));

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(inventory.id())
                .toUri();

        return ResponseEntity.created(uri).body(inventory);
    }
}
