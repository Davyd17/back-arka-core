package com.arka.usecase;

import com.arka.dto.in.CreateShippingDetailIn;
import com.arka.dto.out.ShippingDetailOut;
import com.arka.gateway.repository.ShippingDetailGateway;
import com.arka.mapper.ShippingDetailMapper;
import com.arka.mapper.ShippingDetailMapperImpl;
import com.arka.model.ShippingDetail;
import com.arka.model.enums.ShippingStatus;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RegisterShippingDetailsUseCase {

    private final ShippingDetailGateway gateway;

    private final ShippingDetailMapper shippingDetailMapper =
            new ShippingDetailMapperImpl();

    public ShippingDetailOut execute(CreateShippingDetailIn request){

            ShippingDetail savedShippingDetail = gateway.save(

                    shippingDetailMapper.toDomain(request)
                            .toBuilder()
                            .status(ShippingStatus.PENDING)
                            .build()

            );

        return shippingDetailMapper.toOutDTO(savedShippingDetail);
    }
}
