package com.arka.controller;

import com.arka.cart.dto.AddItemShoppingCartIn;
import com.arka.cart.dto.ShoppingCartOut;
import com.arka.docs.CommonApiResponses;
import com.arka.exceptions.ErrorResponse;
import com.arka.mappers.ShoppingCartRestMapper;
import com.arka.request.AddItemShoppingCartRequest;
import com.arka.response.save.ShoppingCartResponse;
import com.arka.cart.AddItemToShoppingCartUseCase;
import com.arka.cart.ListAbandonedShoppingCartsUseCase;
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
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/shopping-carts")
@RequiredArgsConstructor
@Tag(name = "Shopping Carts",
        description = "Operations for managing customer carts")
public class ShoppingCartController {

    private final AddItemToShoppingCartUseCase addItemToShoppingCartUseCase;
    private final ListAbandonedShoppingCartsUseCase listAbandonedShoppingCartsUseCase;

    private final ShoppingCartRestMapper mapper;

    @Operation(
            summary = "List abandoned shopping carts",
            description = "Retrieves all shopping carts marked as abandoned."
    )
    @CommonApiResponses
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of abandoned carts retrieved successfully",
                    content = @Content(array =
                    @ArraySchema(schema = @Schema(implementation = ShoppingCartResponse.class)))
            )
    })
    @GetMapping("/abandoned")
    public List<ShoppingCartResponse> listAbandonedCarts() {

        List<ShoppingCartOut> outDto =
                listAbandonedShoppingCartsUseCase.execute();

        return outDto.stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Operation(
            summary = "Add item to shopping cart",
            description = "Adds a product and quantity to the authenticated" +
                    " user's active or abandoned shopping cart. If a shopping cart is in abandoned status" +
                    " adding a new product make it get back to active status"
    )
    @CommonApiResponses
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Item added to cart successfully",
                    content = @Content(schema = @Schema(implementation = ShoppingCartResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid quantity or request payload",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping("/items")
    public ResponseEntity<ShoppingCartResponse> addItem(
            @Parameter(hidden = true) Authentication authentication,
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
