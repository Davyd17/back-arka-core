package com.arka.mapper;

import com.arka.dto.in.CreateOrderItemIn;
import com.arka.dto.in.UpdateOrderIn;
import com.arka.dto.in.UpdateOrderItemIn;
import com.arka.dto.out.CreateOrderItemOut;
import com.arka.model.order.OrderItem;
import org.mapstruct.*;

@Mapper(uses = CreateProductOutMapper.class)
public interface OrderItemMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "product.id", source = "productId")
    })
    OrderItem toDomain(CreateOrderItemIn inDTO);

    @Mappings({
            @Mapping(target = "product.id", source = "productId"),
             @Mapping(target = "unitPrice", ignore = true)
    })
    OrderItem toDomain(UpdateOrderItemIn outDTO);


    CreateOrderItemOut toOrderItemDTO(OrderItem domain);
}
