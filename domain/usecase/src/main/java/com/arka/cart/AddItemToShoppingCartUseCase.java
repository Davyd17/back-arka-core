package com.arka.cart;

import com.arka.cart.dto.AddItemShoppingCartIn;
import com.arka.cart.dto.ShoppingCartOut;
import com.arka.cart.mapper.ShoppingCartMapperImpl;
import com.arka.entities.cart.ShoppingCart;
import com.arka.entities.product.Product;
import com.arka.enums.ShoppingCartStatus;
import com.arka.cart.gateway.ShoppingCartGateway;
import com.arka.cart.mapper.ShoppingCartMapper;
import com.arka.product.service.ProductService;
import com.arka.inventory.service.WarehouseInventoryService;
import com.arka.util.NullValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AddItemToShoppingCartUseCase {

    private final ShoppingCartGateway cartGateway;

    private final ProductService productService;
    private final WarehouseInventoryService inventoryService;

    private final ShoppingCartMapper mapper =
            new ShoppingCartMapperImpl();

    public ShoppingCartOut execute(AddItemShoppingCartIn input){

        NullValidator.validate(input, "input");

        inventoryService.validateGeneralStockAvailability(
                input.productId(), input.quantity());

        Product foundProduct =
                productService.findById(input.productId());

        ShoppingCart cart = getOrCreateCart(input.userId());

        cart.addItem(foundProduct, input.quantity());

        return mapper.toOutDto(cartGateway.save(cart));
    }

    private ShoppingCart getOrCreateCart(Long userId){
        return cartGateway.getLastCreatedCart(userId)
                .filter(cart ->
                        cart.getStatus().equals(ShoppingCartStatus.ABANDONED) ||
                        cart.getStatus().equals(ShoppingCartStatus.ACTIVE))
                .orElseGet(() -> ShoppingCart.create(userId));
    }
}
