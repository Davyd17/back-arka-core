package com.arka.controller;

import com.arka.dto.out.ShoppingCartOut;
import com.arka.mappers.ShoppingCartRestMapper;
import com.arka.request.CreateShoppingCartRequest;
import com.arka.response.save.ShoppingCartResponse;
import com.arka.usecase.CreateShoppingCartUseCase;
import com.arka.usecase.ListAbandonedShoppingCartsUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/shopping-carts")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final CreateShoppingCartUseCase createShoppingCartUseCase;
    private final ListAbandonedShoppingCartsUseCase listAbandonedShoppingCartsUseCase;

    private final ShoppingCartRestMapper mapper;

    @GetMapping("/abandoned")
    public List<ShoppingCartResponse> listAbandonedCarts(){

        List<ShoppingCartOut> outDto =
                listAbandonedShoppingCartsUseCase.execute();

        return outDto.stream()
                .map(mapper::toResponse)
                .toList();
    }

    @PostMapping
    public ResponseEntity<ShoppingCartResponse> create
            (@Valid @RequestBody CreateShoppingCartRequest request) {

        ShoppingCartOut shoppingCartOut =
                createShoppingCartUseCase.execute(
                        mapper.toInDto(request));

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(shoppingCartOut.id())
                .toUri();

        ShoppingCartResponse response
                = mapper.toResponse(shoppingCartOut);

        return ResponseEntity.created(uri).body(response);
    }
}
