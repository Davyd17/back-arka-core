package com.arka.usecase;

import com.arka.dto.out.ShoppingCartOut;
import com.arka.exceptions.NotFoundException;
import com.arka.gateway.repository.ShoppingCartRepository;
import com.arka.mapper.ShoppingCartMapper;
import com.arka.mapper.ShoppingCartMapperImpl;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ListAbandonedShoppingCartsUseCase {

    private final ShoppingCartRepository cartsRepository;
    private final ShoppingCartMapper mapper
            = new ShoppingCartMapperImpl();

    public List<ShoppingCartOut> execute() {

        List<ShoppingCartOut> abandonedCarts =
                cartsRepository.getAllAbandonedCarts()
                        .stream()
                        .map(mapper::toOutDto)
                        .toList();

        if (abandonedCarts.isEmpty())
            throw new NotFoundException("No abandoned carts found");

        else return abandonedCarts;
    }

}
