package com.arka.controller;

import com.arka.dto.in.AddItemShoppingCartIn;
import com.arka.dto.out.ShoppingCartOut;
import com.arka.mappers.ShoppingCartRestMapper;
import com.arka.request.AddItemShoppingCartRequest;
import com.arka.response.save.ShoppingCartResponse;
import com.arka.usecase.AddItemToShoppingCartUseCase;
import com.arka.usecase.ListAbandonedShoppingCartsUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/{userId}/items")
    public ResponseEntity<ShoppingCartResponse> addItem
            (@PathVariable Long userId, @Valid @RequestBody AddItemShoppingCartRequest request) {

        ShoppingCartOut shoppingCartOut =
                addItemToShoppingCartUseCase.execute(
                        new AddItemShoppingCartIn(
                                userId,
                                request.productId(),
                                request.quantity()
                        ));

        ShoppingCartResponse response
                = mapper.toResponse(shoppingCartOut);

        return ResponseEntity.ok(response);
    }
}
