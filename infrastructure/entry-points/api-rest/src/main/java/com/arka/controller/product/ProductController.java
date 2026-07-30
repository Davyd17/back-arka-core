package com.arka.controller.product;

import com.arka.mappers.ProductRestMapper;
import com.arka.product.ListAllProductsUseCase;
import com.arka.product.dto.CreateProductOut;
import com.arka.product.dto.ProductSummaryOut;
import com.arka.request.CreateProductRequest;
import com.arka.response.get.ProductSummaryResponse;
import com.arka.response.save.CreateProductResponse;
import com.arka.product.CreateProductUseCase;
import com.arka.util.pagination.PageSortDirection;
import com.arka.util.pagination.PageWrapper;
import com.arka.util.pagination.PageableIn;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(path = "api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final CreateProductUseCase createProductUsecase;
    private final ListAllProductsUseCase listAllProductsUseCase;
    private final ProductRestMapper productMapper;

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

    @GetMapping
    public ResponseEntity<PageWrapper<ProductSummaryResponse>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "DESC") PageSortDirection sortDirection
    ) {

        PageWrapper<ProductSummaryOut> pageOut =
                listAllProductsUseCase.execute(new PageableIn(
                        page, size, sortBy, sortDirection));

        return ResponseEntity.ok(pageOut.map(productMapper::toSummaryResponse));
    }
}
