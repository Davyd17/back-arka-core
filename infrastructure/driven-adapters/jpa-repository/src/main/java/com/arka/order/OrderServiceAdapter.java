package com.arka.order;

import com.arka.gateway.order.OrderGateway;
import com.arka.model.order.Order;
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

            OrderEntity newOrderEntity = mapper.toEntity(newOrder);

            newOrderEntity.getItems().forEach(item -> {
                item.setOrder(newOrderEntity);
            });

            OrderEntity savedOrder = repository
                    .save(newOrderEntity);

            return mapper.toDomain(savedOrder);

        } else throw new IllegalArgumentException(
                "Order can't be null");
    }
}
