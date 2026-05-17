package com.arka.usecase.order;

import com.arka.dto.in.CreateOrderIn;
import com.arka.dto.out.CreateOrderOut;
import com.arka.gateway.order.OrderGateway;
import com.arka.mapper.OrderMapper;
import com.arka.model.Company;
import com.arka.model.order.Order;
import com.arka.model.order.OrderItem;
import com.arka.model.product.Product;
import com.arka.service.CompanyService;
import com.arka.service.ProductService;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class CreateOrderUseCase {

    private final OrderGateway orderGateway;

    private final OrderMapper orderMapper;

    private final CompanyService companyService;
    private final ProductService productService;

    public CreateOrderOut execute(CreateOrderIn input) {

        if (input == null)
            throw new IllegalArgumentException("Order can't be null");

        Order newOrder = this.buildOrder(input);

        this.addItemsToOrder(newOrder, input.items());

        return orderMapper.toDTO(orderGateway.createOrder(newOrder));

    }

    private Order buildOrder(CreateOrderIn input) {

        Company foundCompany = companyService
                .getCompanyById(input.companyId());

        return Order.create(
                input.number(),
                input.notes(),
                input.type(),
                foundCompany
        );
    }

    private void addItemsToOrder(Order order, List<CreateOrderIn.Item> inputItems) {

        inputItems.forEach(item -> {

            Product foundProduct =
                    productService.findById(item.productId());

            OrderItem inputItem =
                    OrderItem.create(foundProduct, item.quantity());

            order.addItem(inputItem);
        });
    }
}
