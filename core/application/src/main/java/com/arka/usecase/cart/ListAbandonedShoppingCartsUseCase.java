package com.arka.usecase.cart;

import com.arka.dto.out.ShoppingCartOut;
import com.arka.exceptions.NotFoundException;
import com.arka.gateway.repository.ShoppingCartGateway;
import com.arka.mapper.ShoppingCartMapper;
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
