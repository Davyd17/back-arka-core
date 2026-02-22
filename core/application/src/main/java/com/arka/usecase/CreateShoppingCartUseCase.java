package com.arka.usecase;

import com.arka.dto.in.CreateShoppingCartIn;
import com.arka.dto.in.ShoppingCartItemIn;
import com.arka.dto.out.CreateShoppingCartOut;
import com.arka.gateway.repository.ShoppingCartRepository;
import com.arka.mapper.ShoppingCartMapper;
import com.arka.model.cart.ShoppingCart;
import com.arka.model.product.Product;
import com.arka.service.ProductService;
import com.arka.service.WarehouseInventoryService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateShoppingCartUseCase {

    private final ProductService productService;
    private final WarehouseInventoryService inventoryService;
    private final ShoppingCartRepository cartRepository;
    private final ShoppingCartMapper mapper;

    public CreateShoppingCartOut execute(CreateShoppingCartIn request) {

        if(request == null)
            throw new IllegalArgumentException("Request can't be null");

        //TODO: Verify user existence
        ShoppingCart newShoppingCart =
                new ShoppingCart(request.user_id());

        for(ShoppingCartItemIn item : request.items()) {

            inventoryService.validateGeneralStockAvailability
                    (item.product_id(), item.quantity());

            Product itemProduct = productService.findById(item.product_id());

            newShoppingCart.addItem(itemProduct, item.quantity());
        }

        cartRepository.save(newShoppingCart);

        return mapper.toCreateDto
                (cartRepository.save(newShoppingCart));
    }
}
