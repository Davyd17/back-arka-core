package com.arka.order;

import com.arka.entities.order.Order;
import com.arka.order.gateway.OrderGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceAdapter implements OrderGateway {

    private final OrderRepository repository;
    private final OrderEntityMapper mapper;

    @Override
    public Order save(Order newOrder) {

        OrderEntity newOrderEntity = mapper.toEntity(newOrder);

        newOrderEntity.getItems().forEach(item -> {
            item.setOrder(newOrderEntity);
        });

        return mapper.toDomain(repository.save(newOrderEntity));
    }

    @Override
    public Optional<Order> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain);

    }

    @Override
    public BigDecimal getTotalRevenueFromDateRange(Instant since, Instant until) {
        return repository.getTotalRevenueFromDateRange(since, until);
    }
}
