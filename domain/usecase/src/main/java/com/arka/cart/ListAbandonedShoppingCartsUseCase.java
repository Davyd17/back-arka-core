package com.arka.cart;

import com.arka.cart.dto.ShoppingCartOut;
import com.arka.exceptions.NotFoundException;
import com.arka.gateway.ShoppingCartGateway;
import com.arka.cart.mapper.ShoppingCartMapper;
import com.arka.mapper.ShoppingCartMapperImpl;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ListAbandonedShoppingCartsUseCase {

    private final ShoppingCartGateway cartGateway;
    private final ShoppingCartMapper mapper
            = new ShoppingCartMapperImpl();

    public List<ShoppingCartOut> execute() {

        List<ShoppingCartOut> abandonedCarts =
                cartGateway.getAllAbandonedCarts()
                        .stream()
                        .map(mapper::toOutDto)
                        .toList();

        if (abandonedCarts.isEmpty())
            throw new NotFoundException("No abandoned carts found");

        else return abandonedCarts;
    }

}
