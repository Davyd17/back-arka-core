package com.arka.mapper;

import com.arka.dto.in.CreateOrderItemIn;
import com.arka.dto.out.CreateOrderItemOut;
import com.arka.model.order.OrderItem;
import org.mapstruct.*;

@Mapper(uses = CreateProductOutMapper.class)
public interface CreateOrderItemMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "product.id", source = "productId")
    })
    OrderItem toDomain(CreateOrderItemIn inDTO);



    CreateOrderItemOut toOrderItemDTO(OrderItem domain);
}
