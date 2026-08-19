package com.arka.controller;

import com.arka.docs.CommonApiResponses;
import com.arka.exceptions.ErrorResponse;
import com.arka.mappers.CompanyRestMapper;
import com.arka.party.dto.CompanyOut;
import com.arka.request.CreateCompanyRequest;
import com.arka.response.save.CreateCompanyResponse;
import com.arka.party.CreateSupplierUseCase;
import com.arka.party.ListSuppliersByCategoryUseCase;
import com.arka.response.get.CompanyResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/suppliers")
@RequiredArgsConstructor
@Validated
@Tag(name = "Suppliers", description = "Operations for managing supplier companies and categories")
public class SupplierController {

    private final ListSuppliersByCategoryUseCase listSupplierByCategory;
    private final CreateSupplierUseCase createSupplierUseCase;

    private final CompanyRestMapper mapper;

    @Operation(
            summary = "List suppliers by category",
            description = "Retrieves all suppliers belonging to a specific category ID."
    )
    @CommonApiResponses
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Suppliers retrieved successfully",
                    content = @Content(array =
                    @ArraySchema(schema = @Schema(implementation = CompanyResponse.class)))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Category not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping("/categories/{id}")
    public List<CompanyResponse> listById(@PathVariable @NotNull Long id) {

        return listSupplierByCategory.execute(id)
                        .stream()
                        .map(mapper::toResponse)
                        .toList();
    }

    @Operation(
            summary = "Create supplier",
            description = "Registers a new supplier company in the system."
    )
    @CommonApiResponses
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Supplier created successfully",
                    content = @Content(schema = @Schema(implementation = CreateCompanyResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request payload or validation error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping
    public ResponseEntity<CreateCompanyResponse> save(@Valid @RequestBody CreateCompanyRequest request){

        CompanyOut savedSupplier = createSupplierUseCase
                .execute(mapper.toInput(request));

        CreateCompanyResponse response = mapper.toCreateResponse(savedSupplier);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }

}
