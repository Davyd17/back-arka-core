package com.arka.usecase;

import com.arka.dto.in.CreateShoppingCartIn;
import com.arka.dto.in.ShoppingCartItemIn;
import com.arka.dto.out.ShoppingCartOut;
import com.arka.gateway.repository.ShoppingCartRepository;
import com.arka.mapper.ShoppingCartMapper;
import com.arka.mapper.ShoppingCartMapperImpl;
import com.arka.service.ProductService;
import com.arka.service.WarehouseInventoryService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Deprecated
public class CreateShoppingCartUseCase {

    private final ProductService productService;
    private final WarehouseInventoryService inventoryService;
    private final ShoppingCartRepository cartRepository;
    private final ShoppingCartMapper mapper =
            new ShoppingCartMapperImpl();

    public ShoppingCartOut execute(CreateShoppingCartIn request) {

        return null;
    }
}
