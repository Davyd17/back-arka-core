package com.arka.controller.product;

import com.arka.mappers.ProductRestMapper;
import com.arka.product.dto.CreateProductOut;
import com.arka.request.CreateProductRequest;
import com.arka.response.save.CreateProductResponse;
import com.arka.product.CreateProductUseCase;
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
    private final ProductRestMapper productMapper;

    @PostMapping
    public ResponseEntity<CreateProductResponse> save(@Valid @RequestBody CreateProductRequest request){

        CreateProductOut product = createProductUsecase
                .execute(productMapper.toInput(request));

        URI uri = URI.create(Long.toString(product.id()));

        return ResponseEntity.created(uri)
                .body(productMapper.toCreateResponse(product));
    }
}
