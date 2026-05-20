package com.arka.order;

import com.arka.entities.Company;
import com.arka.entities.order.Order;
import com.arka.entities.order.OrderItem;
import com.arka.model.product.Product;
import com.arka.order.dto.CreateOrderIn;
import com.arka.order.dto.CreateOrderOut;
import com.arka.order.gateway.OrderGateway;
import com.arka.order.mapper.OrderMapper;
import com.arka.party.service.CompanyService;
import com.arka.product.service.ProductService;
import com.arka.util.NullValidator;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class CreateOrderUseCase {

    private final OrderGateway orderGateway;

    private final OrderMapper orderMapper;

    private final CompanyService companyService;
    private final ProductService productService;

    public CreateOrderOut execute(CreateOrderIn input) {

        NullValidator.validate(input, "input");

        Order newOrder = this.buildOrder(input);

        this.addItemsToOrder(newOrder, input.items());

        return orderMapper.toCreateOut(orderGateway.save(newOrder));

    }

    private Order buildOrder(CreateOrderIn input) {

        Company foundCompany = companyService
                .findById(input.companyId());

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
