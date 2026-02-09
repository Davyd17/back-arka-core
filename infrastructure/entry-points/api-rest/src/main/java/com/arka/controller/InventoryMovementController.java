package com.arka.controller;

import com.arka.mappers.InventoryMovementRequestMapper;
import com.arka.model.inventory.InventoryMovement;
import com.arka.request.CreateInventoryMovementRequest;
import com.arka.usecase.GenerateInventoryMovementUseCase;
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

    private final GenerateInventoryMovementUseCase itemEntryToInventoryUseCase;

    private final InventoryMovementRequestMapper mapper;

    @PostMapping
    public ResponseEntity<InventoryMovement> create(@Valid @RequestBody CreateInventoryMovementRequest request){

        InventoryMovement inventory =
                itemEntryToInventoryUseCase.execute(mapper.toInDTO(request));

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(inventory.getId())
                .toUri();

        return ResponseEntity.created(uri).body(inventory);
    }
}
