package com.arka.service;

import com.arka.mapper.OrderItemMapper;
import com.arka.mapper.OrderItemMapperImpl;
import com.arka.model.enums.OrderType;
import com.arka.model.order.OrderItem;
import com.arka.model.product.Product;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class OrderItemService {

    private final ProductService productService;
    private final WarehouseInventoryService inventoryService;

    private final OrderItemMapper orderItemMapper =
            new OrderItemMapperImpl();

    public List<OrderItem> resolveProductsFromOrderItemsRequest(
            List<OrderItem> newItems, OrderType orderType) {

        if (newItems == null || newItems.isEmpty())
            throw new IllegalArgumentException("Order must contain at least one item");

        for (OrderItem item : newItems) {

            Product foundProduct = productService.findById(item.getProduct().getId());

            if (orderType.equals(OrderType.SALES))
                inventoryService.validateGeneralStockAvailability(
                        foundProduct.getId(), item.getQuantity());

            item.addProduct(foundProduct);
        }

        return newItems;
    }

}
