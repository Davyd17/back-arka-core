package com.arka.controller;

import com.arka.docs.CommonApiResponses;
import com.arka.entities.product.ProductCategory;
import com.arka.exceptions.ErrorResponse;
import com.arka.mappers.ProductRestMapper;
import com.arka.product.ListAllProductsUseCase;
import com.arka.product.ListProductCategoriesUseCase;
import com.arka.product.dto.CreateProductOut;
import com.arka.product.dto.ProductSummaryOut;
import com.arka.request.CreateProductRequest;
import com.arka.response.get.ProductSummaryResponse;
import com.arka.response.save.CreateOrderResponse;
import com.arka.response.save.CreateProductResponse;
import com.arka.product.CreateProductUseCase;
import com.arka.util.pagination.PageSortDirection;
import com.arka.util.pagination.PageWrapper;
import com.arka.util.pagination.PageableIn;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Product management operations")
public class ProductController {

    private final ListProductCategoriesUseCase listCategoriesUseCase;
    private final CreateProductUseCase createProductUsecase;
    private final ListAllProductsUseCase listAllProductsUseCase;
    private final ProductRestMapper productMapper;

    @Operation(summary = "Create new product")
    @CommonApiResponses
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product created successfully",
                    content = @Content(schema = @Schema(implementation = CreateOrderResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request or validation error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))

    })
    @PostMapping
    public ResponseEntity<CreateProductResponse> save(@Valid @RequestBody CreateProductRequest request) {

        CreateProductOut product = createProductUsecase
                .execute(productMapper.toInput(request));

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product.id())
                .toUri();

        return ResponseEntity.created(uri)
                .body(productMapper.toCreateResponse(product));
    }

    @Operation(
            summary = "Get paginated list of products",
            description = "Retrieves a paginated and sorted list of product summaries. Public endpoint."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved products page",
                    content = @Content(schema = @Schema(
                            implementation = PageWrapper.class, contentSchema = ProductSummaryResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid pagination or sorting parameters",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Unexpected server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping
    public ResponseEntity<PageWrapper<ProductSummaryResponse>> findAll(
            @Parameter(description = "Zero-based page index", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of records per page", example = "20")
            @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Field name to sort by", example = "name")
            @RequestParam(defaultValue = "name") String sortBy,
            @Parameter(description = "Sort direction (ASC or DESC)", example = "DESC")
            @RequestParam(defaultValue = "DESC") PageSortDirection sortDirection
    ) {

        PageWrapper<ProductSummaryOut> pageOut =
                listAllProductsUseCase.execute(new PageableIn(
                        page, size, sortBy, sortDirection));

        return ResponseEntity.ok(pageOut.map(productMapper::toSummaryResponse));
    }

    @Operation(
            summary = "List all product categories",
            description = "Retrieves a list of all registered product categories in the system."
    )
    @CommonApiResponses
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Product categories retrieved successfully",
                    content = @Content(array =
                    @ArraySchema(schema = @Schema(implementation = ProductCategory.class)))
            )
    })
    @GetMapping
    public List<ProductCategory> listAll(){
        return listCategoriesUseCase.execute();
    }
}
