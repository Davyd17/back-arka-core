package com.arka.shipping;

import com.arka.shipping.dto.CreateShippingDetailIn;
import com.arka.shipping.dto.ShippingDetailOut;
import com.arka.shipping.gateway.ShippingDetailGateway;
import com.arka.shipping.mapper.ShippingDetailMapper;
import com.arka.model.ShippingDetail;
import com.arka.model.information.Address;
import com.arka.model.order.Order;
import com.arka.party.service.AddressService;
import com.arka.order.service.OrderService;
import com.arka.shipping.mapper.ShippingDetailMapperImpl;
import com.arka.util.NullValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RegisterShippingDetailsUseCase {

    private final ShippingDetailGateway gateway;
    private final OrderService orderService;
    private final AddressService addressService;

    private final ShippingDetailMapper shippingDetailMapper =
            new ShippingDetailMapperImpl();

    public ShippingDetailOut execute(CreateShippingDetailIn input) {

        NullValidator.validate(input, "input");

        ShippingDetail savedShippingDetail =
                gateway.save(buildShippingDetail(input));

        return shippingDetailMapper.toOutDTO(savedShippingDetail);
    }

    private ShippingDetail buildShippingDetail(CreateShippingDetailIn input){

        Order foundOrder = orderService.findById(input.orderId());
        Address foundOriginAddress =
                addressService.findById(input.originAddressId());
        Address foundDestinationAddress =
                addressService.findById(input.destinationAddressId());

        return ShippingDetail.create(
                input.carrier(),
                input.trackingNumber(),
                foundOrder,
                input.notes(),
                foundOriginAddress,
                foundDestinationAddress);
    }
}
