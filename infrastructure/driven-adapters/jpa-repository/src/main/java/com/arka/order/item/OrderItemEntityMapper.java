package com.arka.order.item;
import com.arka.model.order.OrderItem;
import com.arka.product.ProductEntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ProductEntityMapper.class)
public interface OrderItemEntityMapper {

    @Mapping(target = "order", ignore = true)
    OrderItemEntity toEntity(OrderItem domain);

    OrderItem toDomain(OrderItemEntity entity);


}
