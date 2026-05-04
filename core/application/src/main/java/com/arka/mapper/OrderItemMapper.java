package com.arka.mapper;

<<<<<<< Updated upstream
import com.arka.dto.in.CreateOrderIn;
import com.arka.dto.in.CreateOrderItemIn;
=======
>>>>>>> Stashed changes
import com.arka.dto.in.UpdateOrderIn;
import com.arka.dto.in.UpdateOrderItemIn;
import com.arka.dto.out.CreateOrderItemOut;
import com.arka.model.order.OrderItem;
import org.mapstruct.*;

import java.util.List;

@Mapper(uses = CreateProductOutMapper.class)
public interface OrderItemMapper {

//    @Mappings({
//            @Mapping(target = "id", ignore = true),
//            @Mapping(target = "product.id", source = "productId")
//    })
//    OrderItem toDomain(UpdateOrderIn.Item inDTO);
//
//    @Mappings({
//            @Mapping(target = "product.id", source = "productId"),
//             @Mapping(target = "unitPrice", ignore = true)
//    })
//    OrderItem toDomain(UpdateOrderItemIn outDTO);
//
//    List<OrderItem> toDomainList(List<UpdateOrderIn.Item> outDTOs);
//
//    CreateOrderItemOut toOrderItemDTO(OrderItem domain);
}
