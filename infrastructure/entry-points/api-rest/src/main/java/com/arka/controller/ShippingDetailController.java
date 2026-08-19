package com.arka.controller;

import com.arka.docs.CommonApiResponses;
import com.arka.exceptions.ErrorResponse;
import com.arka.shipping.dto.ShippingDetailOut;
import com.arka.mappers.ShippingDetailRestMapper;
import com.arka.request.CreateShippingDetailRequest;
import com.arka.response.save.CreateShippingDetailResponse;
import com.arka.shipping.RegisterShippingDetailsUseCase;
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

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/v1/shipping-details")
@Tag(name = "Shipping Details", description = "Operations for managing shipment addresses, tracking, and carrier details")
public class ShippingDetailController {

    private final RegisterShippingDetailsUseCase registerShippingDetailsUseCase;

    private final ShippingDetailRestMapper mapper;

    @Operation(
            summary = "Register shipping details",
            description = "Creates shipping and delivery details associated with an order."
    )
    @CommonApiResponses
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Shipping details registered successfully",
                    content = @Content(schema = @Schema(implementation = CreateShippingDetailResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request payload or validation error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Referenced order not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping
    public ResponseEntity<CreateShippingDetailResponse> registerShippingDetails(
            @Valid @RequestBody CreateShippingDetailRequest request){

        ShippingDetailOut registeredShippingDetails =
                registerShippingDetailsUseCase.execute(mapper.toDomain(request));

        CreateShippingDetailResponse response = mapper.toResponse(registeredShippingDetails);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }
}
