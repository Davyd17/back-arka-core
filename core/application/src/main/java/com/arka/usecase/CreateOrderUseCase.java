package com.arka.usecase;

import com.arka.dto.in.CreateOrderIn;
import com.arka.dto.in.CreateOrderItemIn;
import com.arka.dto.out.CreateOrderOut;
import com.arka.repository.order.OrderGateway;
import com.arka.mapper.*;
import com.arka.mapper.OrderMapperImpl;
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

    private final OrderMapper orderMapper =
            new OrderMapperImpl();

    private final CreateOrderItemMapper orderItemMapper =
            new CreateOrderItemMapperImpl();

    private final CompanyService companyService;
    private final ProductService productService;

    public CreateOrderOut execute(CreateOrderIn createOrderIn) {

        if (Objects.nonNull(createOrderIn)) {

            Company embbededCompany = companyService
                    .getCompanyById(createOrderIn.companyId());


            Set<OrderItem> embeddedOrderItems = new HashSet<>();

            for (CreateOrderItemIn item : createOrderIn.items()){

                Product foundProduct = productService
                        .findById(item.productId());

                embeddedOrderItems.add(
                        orderItemMapper.toDomain(item)
                        .toBuilder()
                        .product(foundProduct)
                        .build());
            }

            Order newOrder =
                    orderGateway.createOrder(
                            orderMapper.toDomain(createOrderIn)
                                    .toBuilder()
                                    .company(embbededCompany)
                                    .items(embeddedOrderItems)
                                    .build()
                    );

            return orderMapper
                    .toDTO(newOrder);

        } else throw new IllegalArgumentException(
                "Order can't be null"
        );
    }
}
