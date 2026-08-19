package com.arka.controller;

import com.arka.docs.CommonApiResponses;
import com.arka.exceptions.ErrorResponse;
import com.arka.mappers.InventoryMovementRestMapper;
import com.arka.request.CreateInventoryMovementRequest;
import com.arka.inventory.RegisterInventoryMovementUseCase;
import com.arka.response.save.CreateInventoryMovementResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Inventory Movements",
        description = "Operations for registering and tracking stock movements across warehouses")
public class InventoryMovementController {

    private final RegisterInventoryMovementUseCase registerInventoryMovementUseCase;

    private final InventoryMovementRestMapper mapper;

    @Operation(
            summary = "Register inventory movement",
            description = "Registers a new inventory movement (e.g., IN, OUT) between stock locations."
    )
    @CommonApiResponses
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Inventory movement registered successfully",
                    content = @Content(schema = @Schema(implementation = CreateInventoryMovementResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request payload or insufficient stock",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Referenced warehouse or product not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
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
