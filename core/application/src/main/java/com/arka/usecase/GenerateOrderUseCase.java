package com.arka.usecase;

import com.arka.dto.in.CreateOrderIn;
import com.arka.dto.out.CreateOrderOut;
import com.arka.model.enums.OrderStatus;
import com.arka.gateway.repository.order.OrderGateway;
import com.arka.mapper.OrderMapperImpl;
import com.arka.mapper.OrderMapper;
import com.arka.model.Company;
import com.arka.model.order.Order;
import com.arka.service.CompanyService;
import com.arka.service.ProductService;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class GenerateOrderUseCase {

    private final OrderGateway orderGateway;

    private final OrderMapper orderMapper =
            new OrderMapperImpl();

    private final CompanyService companyService;
    private final ProductService productService;

    public CreateOrderOut execute(CreateOrderIn createOrderIn) {

        if (Objects.nonNull(createOrderIn)) {

            Order newOrder = orderMapper.toDomain(createOrderIn);

            Company foundCompany = companyService
                    .getCompanyById(createOrderIn.companyId());

            newOrder.addCompany(foundCompany);

            newOrder.getItems().forEach(item -> {
                                item.addProduct(
                                        productService.findById(item.getProduct().getId()));
                            });

            newOrder.updateTotalPrice();

            newOrder.updateStatus(OrderStatus.PENDING);

            return orderMapper.toDTO(orderGateway.createOrder(newOrder));

        } else throw new IllegalArgumentException(
                "Order can't be null"
        );
    }
}
