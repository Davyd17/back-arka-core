package com.arka.usecase;

import com.arka.dto.in.CreateOrderIn;
import com.arka.dto.in.CreateOrderItemIn;
import com.arka.dto.out.CreateOrderOut;
import com.arka.gateway.order.OrderGateway;
import com.arka.mapper.CreateOrderItemMapper;
import com.arka.mapper.CreateOrderItemMapperImpl;
import com.arka.mapper.CreateOrderMapper;
import com.arka.mapper.CreateOrderMapperImpl;
import com.arka.model.Company;
import com.arka.model.order.Order;
import com.arka.model.order.OrderItem;
import com.arka.model.product.Product;
import com.arka.service.CompanyService;
import com.arka.service.ProductService;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CreateOrderUseCase {

    private final OrderGateway orderGateway;

    private final CreateOrderMapper createOrderMapper =
            new CreateOrderMapperImpl();

    private final CreateOrderItemMapper orderItemMapper =
            new CreateOrderItemMapperImpl();

    private final CompanyService companyService;
    private final ProductService productService;

    public CreateOrderOut execute(CreateOrderIn createOrderIn) {

        if (Objects.nonNull(createOrderIn)) {

            Company embbededCompany = companyService
                    .getCompanyById(createOrderIn.companyId());

            Map<Long, Product> foundProductWithId = new HashMap<>();

            for (CreateOrderItemIn item :
                    createOrderIn.items()) {

                Product foundProduct =
                        productService.findById(item.productId());

                foundProductWithId.put(foundProduct.getId(), foundProduct);
            }

            Order newOrder =
                    orderGateway.createOrder(
                            createOrderMapper.toDomain(createOrderIn)
                    );


            Set<OrderItem> resolvedItems = newOrder
                    .getItems().stream()
                            .map(createdItem -> createdItem.toBuilder()
                                        .product(foundProductWithId
                                                .get(createdItem.getProduct().getId()))
                                        .build()).collect(Collectors.toSet());

            return createOrderMapper
                    .toDTO(newOrder
                            .toBuilder()
                            .company(embbededCompany)
                            .items(resolvedItems)
                            .build());

        } else throw new IllegalArgumentException(
                "Order can't be null"
        );
    }
}
