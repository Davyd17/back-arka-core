package com.arka.usecase;

import com.arka.dto.in.CreateOrderIn;
import com.arka.dto.out.CreateOrderOut;
import com.arka.gateway.OrderGateway;
import com.arka.mapper.CreateOrderMapper;
import com.arka.model.Company;
import com.arka.model.Order;
import com.arka.service.CompanyService;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class CreateOrderUseCase {

    private final OrderGateway orderGateway;
    private final CreateOrderMapper createOrderMapper;
    private final CompanyService companyService;

    public CreateOrderOut execute(CreateOrderIn createOrderIn){

        if(Objects.nonNull(createOrderIn)){

            Company embbededCompany = companyService
                    .getCompanyByName(
                            createOrderIn.company().name());

            Order newOrder =
                    orderGateway.createOrder(
                         createOrderMapper.toDomain(createOrderIn)
                    );

            return createOrderMapper.toDTO(newOrder
                    .toBuilder()
                    .company(embbededCompany)
                    .build());

        } else throw new IllegalArgumentException(
                "Order can't be null"
        );
    }
}
