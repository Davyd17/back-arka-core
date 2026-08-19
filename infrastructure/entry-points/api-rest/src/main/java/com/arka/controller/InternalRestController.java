package com.arka.controller;

import com.arka.exceptions.ErrorResponse;
import com.arka.mappers.ContactRestMapper;
import com.arka.party.CreateContactUseCase;
import com.arka.party.dto.ContactOutput;
import com.arka.request.CreateContactRequest;
import com.arka.response.get.ContactResponse;
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

@RestController
@RequestMapping("api/v1/internal")
@RequiredArgsConstructor
@Tag(name = "Internal Operations",
        description = "Restricted system endpoints for internal services and network communication")
public class InternalRestController {

    private final CreateContactUseCase createContactUseCase;
    private final ContactRestMapper contactRestMapper;

    @Operation(
            summary = "[INTERNAL] Create contact record",
            description = "**Restricted**: Creates a contact entry. Intended solely for internal inter-service communication.",
            deprecated = true
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Contact created successfully",
                    content = @Content(schema = @Schema(implementation = ContactResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request payload or validation error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - internal network access required",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Unexpected internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping("/contacts")
    public ResponseEntity<ContactResponse> save(
            @Valid @RequestBody CreateContactRequest request) {

        ContactOutput contactOutput = createContactUseCase.execute(
                contactRestMapper.toCreateInput(request));

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(contactOutput.id())
                .toUri();

        return ResponseEntity.created(uri).body(
                contactRestMapper.toResponse(contactOutput));
    }
}
