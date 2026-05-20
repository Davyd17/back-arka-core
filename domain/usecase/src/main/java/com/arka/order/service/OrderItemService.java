package com.arka.order.service;

import com.arka.entities.order.OrderItem;
import com.arka.entities.product.Product;
import com.arka.enums.OrderType;
import com.arka.product.service.ProductService;
import com.arka.inventory.service.WarehouseInventoryService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OrderItemService {

    private final ProductService productService;
    private final WarehouseInventoryService inventoryService;

    public OrderItem resolveItem(OrderType type, Long productId, int quantity){

        Product resolvedProduct =
                this.productService.findById(productId);

        validateStockIfRequired(
                type,
                resolvedProduct.getId(),
                quantity);

        return OrderItem.create(resolvedProduct, quantity);
    }

    private void validateStockIfRequired(OrderType type, Long productId, int quantity){

        if(type.equals(OrderType.SALES))
            this.inventoryService.validateGeneralStockAvailability
                    (productId, quantity);
    }

}
