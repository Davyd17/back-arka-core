package com.arka.shippingdetail;

import com.arka.entities.ShippingDetail;
import com.arka.shipping.gateway.ShippingDetailGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShippingDetailServiceAdapter implements ShippingDetailGateway {

    private final ShippingDetailRepository repository;
    private final ShippingDetailEntityMapper mapper;

    @Override
    public ShippingDetail save(ShippingDetail shippingDetail) {

        ShippingDetailEntity entity = mapper.toEntity(shippingDetail);
        return mapper.toDomain(repository.save(entity));

    }
}
