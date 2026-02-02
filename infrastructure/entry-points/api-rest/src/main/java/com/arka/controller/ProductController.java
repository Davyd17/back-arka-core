package com.arka.controller;

import com.arka.dto.out.CreateProductOut;
import com.arka.mappers.response.CreateResponseMapper;
import com.arka.mappers.request.CreateProductRequestMapper;
import com.arka.request.CreateProductRequest;
import com.arka.response.save.CreateProductResponse;
import com.arka.usecase.CreateProductUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(path = "api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final CreateProductUseCase createProductUsecase;
    private final CreateProductRequestMapper requestMapper;
    private final CreateResponseMapper responseMapper;

    @PostMapping
    public ResponseEntity<CreateProductResponse> save(@Valid @RequestBody CreateProductRequest request){

        CreateProductOut product = createProductUsecase
                .execute(requestMapper.toDomain(request));

        URI uri = URI.create(Long.toString(product.id()));

        return ResponseEntity.created(uri)
                .body(responseMapper.toResponse(product));
    }
}
