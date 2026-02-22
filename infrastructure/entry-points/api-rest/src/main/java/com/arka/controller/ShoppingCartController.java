package com.arka.controller;

import com.arka.dto.out.CreateShoppingCartOut;
import com.arka.mappers.ShoppingCartRestMapper;
import com.arka.request.CreateShoppingCartRequest;
import com.arka.response.save.CreateShoppingCartResponse;
import com.arka.usecase.CreateShoppingCartUseCase;
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
@RequestMapping(path = "api/v1/shopping-carts")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final CreateShoppingCartUseCase createShoppingCartUseCase;
    private final ShoppingCartRestMapper mapper;

    @PostMapping
    public ResponseEntity<CreateShoppingCartResponse> create
            (@Valid @RequestBody CreateShoppingCartRequest request) {

        CreateShoppingCartOut createShoppingCartOut =
                createShoppingCartUseCase.execute(
                        mapper.toInDto(request));

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createShoppingCartOut.id())
                .toUri();

        CreateShoppingCartResponse response
                = mapper.toResponse(createShoppingCartOut);

        return ResponseEntity.created(uri).body(response);
    }
}
