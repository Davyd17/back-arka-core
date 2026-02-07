package com.arka.shippingdetail;

import com.arka.exceptions.NotFoundException;
import com.arka.gateway.ShippingDetailGateway;
import com.arka.information.address.AddressEntity;
import com.arka.information.address.AddressEntityRepository;
import com.arka.model.ShippingDetail;
import com.arka.order.OrderEntity;
import com.arka.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ShippingDetailServiceAdapter implements ShippingDetailGateway {

    private final ShippingDetailRepository repository;
    private final AddressEntityRepository addressRepository;
    private final OrderRepository orderRepository;
    private final ShippingDetailEntityMapper mapper;

    @Override
    public ShippingDetail save(ShippingDetail shippingDetail) {

        if (Objects.nonNull(shippingDetail)) {

            OrderEntity foundOrder = orderRepository
                    .findById(shippingDetail.getOrder().getId())
                    .orElseThrow(() -> new NotFoundException(
                            "Order not found, please enter a valid one"));

            AddressEntity destination = addressRepository.
                    findById(shippingDetail.getDestinationAddress().id())
                    .orElseThrow(() -> new NotFoundException(
                            "Address not found, please enter a valid one"));

            AddressEntity origin = addressRepository
                    .findById(shippingDetail.getOriginAddress().id())
                    .orElseThrow(() -> new NotFoundException(
                            "Address not found, please enter a valid one"));

            ShippingDetailEntity shippingDetailEntity =
                    mapper.toEntity(shippingDetail)
                            .toBuilder()
                            .destinationAddress(destination)
                            .originAddress(origin)
                            .order(foundOrder)
                            .build();

            return mapper.toDomain(repository.save(shippingDetailEntity));

        } else throw new IllegalArgumentException("ShippingDetail cannot be null");
    }
}
