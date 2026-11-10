package com.arka.order;

import com.arka.exceptions.NotFoundException;
import com.arka.order.dto.OrderOut;
import com.arka.order.gateway.OrderGateway;
import com.arka.order.mapper.OrderMapper;
import com.arka.party.gateway.ContactGateway;
import com.arka.util.NullValidator;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;

import java.util.List;

@RequiredArgsConstructor
public class FindOrdersByEmailUseCase {

    private final ContactGateway contactGateway;
    private final OrderGateway orderGateway;
    private final OrderMapper mapper = Mappers.getMapper(OrderMapper.class);

    public List<OrderOut> execute(String contactEmail){

        NullValidator.validate(contactEmail, "Email");

        if (!contactGateway.existsByEmail(contactEmail))
            throw new NotFoundException
                    ("contact with email: " + contactEmail + " not found");

        return orderGateway.findByContact(contactEmail).stream()
                .map(mapper::toFindDTO)
                .toList();

    }
}
