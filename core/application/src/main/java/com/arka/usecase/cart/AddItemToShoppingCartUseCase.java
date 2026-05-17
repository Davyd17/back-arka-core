package com.arka.usecase.cart;

import com.arka.dto.in.AddItemShoppingCartIn;
import com.arka.dto.out.ShoppingCartOut;
import com.arka.gateway.ShoppingCartGateway;
import com.arka.mapper.ShoppingCartMapper;
import com.arka.mapper.ShoppingCartMapperImpl;
import com.arka.model.cart.ShoppingCart;
import com.arka.model.product.Product;
import com.arka.service.ProductService;
import com.arka.service.WarehouseInventoryService;
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
                .orElseGet(() -> ShoppingCart.create(userId));
    }
}
