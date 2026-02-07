package com.arka.model.order;

import com.arka.model.Company;
import com.arka.model.enums.OrderStatus;
import com.arka.model.enums.OrderType;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.Set;

@Getter
@Builder(toBuilder = true)
public class Order {

    private Long id;
    private String number;
    private OrderStatus status;
    private String notes;
    private OrderType type;
    private Instant createdAt;
    private Instant updatedAt;
    private Company company;
    private Set<OrderItem> items;

}
