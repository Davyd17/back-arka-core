package com.arka.order;

import com.arka.repository.order.OrderGateway;
import com.arka.model.order.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

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

    @Override
    public Optional<Order> findById(Long id) {

        if(Objects.nonNull(id))
            return repository.findById(id)
                    .map(mapper::toDomain);

        else throw new IllegalArgumentException(
                "Order id can't be null");
    }

    @Override
    public Order update(Order order) {

        if(Objects.nonNull(order)){

            OrderEntity orderEntity = mapper.toEntity(order);

            orderEntity.getItems().forEach(item -> {
                item.setOrder(orderEntity);
            });

            OrderEntity updatedOrder = repository
                    .save(orderEntity);

            return mapper.toDomain(updatedOrder);

        } else throw new IllegalArgumentException(
                "Order can't be null");
    }
}
