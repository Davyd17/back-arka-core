package com.arka.controller;

import com.arka.cart.dto.AddItemShoppingCartIn;
import com.arka.cart.dto.ShoppingCartOut;
import com.arka.mappers.ShoppingCartRestMapper;
import com.arka.request.AddItemShoppingCartRequest;
import com.arka.response.save.ShoppingCartResponse;
import com.arka.cart.AddItemToShoppingCartUseCase;
import com.arka.cart.ListAbandonedShoppingCartsUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/shopping-carts")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final AddItemToShoppingCartUseCase addItemToShoppingCartUseCase;
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

    @PostMapping("/items")
    public ResponseEntity<ShoppingCartResponse> addItem(
            Authentication authentication,
             @Valid @RequestBody AddItemShoppingCartRequest request) {

        String callerEmail = authentication.getName();

        ShoppingCartOut shoppingCartOut = addItemToShoppingCartUseCase
                .execute(new AddItemShoppingCartIn(request.productId(), request.quantity()),
                        callerEmail);

        ShoppingCartResponse response
                = mapper.toResponse(shoppingCartOut);

        return ResponseEntity.ok(response);
    }
}
