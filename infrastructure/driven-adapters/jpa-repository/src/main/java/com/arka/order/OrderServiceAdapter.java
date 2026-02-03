package com.arka.order;

import com.arka.gateway.OrderGateway;
import com.arka.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrderServiceAdapter implements OrderGateway {

    private final OrderRepository repository;
    private final OrderEntityMapper mapper;

    @Override
    public Order createOrder(Order newOrder) {

        if(Objects.nonNull(newOrder)){

            OrderEntity savedOrder = repository
                    .save(mapper.toEntity(newOrder));

            return mapper.toDomain(savedOrder);

        } else throw new IllegalArgumentException(
                "Order can't be null");
    }
}
