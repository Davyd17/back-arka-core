package com.arka.usecase;

import com.arka.dto.in.CreateShoppingCartIn;
import com.arka.dto.out.ShoppingCartOut;
import com.arka.gateway.repository.ShoppingCartRepository;
import com.arka.mapper.ShoppingCartMapper;
import com.arka.mapper.ShoppingCartMapperImpl;
import com.arka.model.cart.ShoppingCart;
import com.arka.model.product.Product;
import com.arka.service.ProductService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class AddItemToShoppingCartUseCase {

    private final ShoppingCartRepository cartRepository;
    private final ProductService productService;
    private final ShoppingCartMapper mapper =
            new ShoppingCartMapperImpl();

    public ShoppingCartOut execute(CreateShoppingCartIn request){

        if(request == null)
            throw new IllegalArgumentException("Request can't be null");

        Product foundProduct =
                productService.findById(request.item().productId());

        Optional<ShoppingCart> activeCart = cartRepository
                .getActiveCartByUserId(request.userId())
                .map(cart -> {

                    cart.addItem(foundProduct, request.item().quantity());
                    return cart;

                });

        if(activeCart.isPresent())
            return mapper.toOutDto(cartRepository.update(activeCart.get()));

         else {

            ShoppingCart newCart = new ShoppingCart(request.userId());
            newCart.addItem(foundProduct, request.item().quantity());

            return mapper.toOutDto(newCart);
        }


    }
}
