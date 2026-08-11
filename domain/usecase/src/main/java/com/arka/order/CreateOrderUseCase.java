package com.arka.order;

import com.arka.entities.information.Contact;
import com.arka.entities.order.Order;
import com.arka.entities.order.OrderItem;
import com.arka.entities.product.Product;
import com.arka.order.dto.CreateOrderIn;
import com.arka.order.dto.CreateOrderOut;
import com.arka.order.gateway.OrderGateway;
import com.arka.order.mapper.OrderMapper;
import com.arka.party.service.ContactService;
import com.arka.product.service.ProductService;
import com.arka.util.NullValidator;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;

import java.util.*;

@RequiredArgsConstructor
public class CreateOrderUseCase {

    private final OrderGateway orderGateway;

    private final OrderMapper orderMapper =
            Mappers.getMapper(OrderMapper.class);

    private final ContactService contactService;
    private final ProductService productService;

    public CreateOrderOut execute(CreateOrderIn input, String email) {

        NullValidator.validate(input, "input");

        Contact existingContact = contactService.findByEmail(email);

        Order newOrder = this.buildOrder(input, existingContact);

        this.addItemsToOrder(newOrder, input.items());

        return orderMapper.toCreateOut(orderGateway.save(newOrder));

    }

    private Order buildOrder(CreateOrderIn input, Contact existingContact) {

        return Order.create(
                input.notes(),
                input.type(),
                existingContact
        );
    }

    private void addItemsToOrder(Order order, List<CreateOrderIn.Item> inputItems) {

        inputItems.forEach(item -> {

            Product foundProduct =
                    productService.findById(item.productId());

            OrderItem inputItem =
                    OrderItem.create(foundProduct, item.quantity());

            order.addItem(inputItem);
        });
    }
}
