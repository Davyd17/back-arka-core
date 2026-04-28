package com.arka.model.order;

import com.arka.exceptions.InvalidOrderStatusException;
import com.arka.model.Company;
import com.arka.enums.OrderStatus;
import com.arka.enums.OrderType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class Order {

    private Long id;
    private String number;
    private OrderStatus status;
    private String notes;
    private OrderType type;
    private BigDecimal totalPrice;
    private Instant createdAt;
    private Instant updatedAt;
    private Company company;
    private List<OrderItem> items;

    public static Order create(String number,
                               String notes,
                               OrderType type,
                               Company company) {

        return Order.builder()
                .number(number)
                .status(OrderStatus.PENDING)
                .notes(notes)
                .type(type)
                .createdAt(Instant.now())
                .company(company)
                .items(new ArrayList<>())
                .build();
    };

    public void addItem(OrderItem item){

        this.status.validateEditable(this.number);
        this.items.add(item);
        this.updateTotalPrice();
    }

    public void removeItem(Long productId){

        this.status.validateEditable(this.number);

        this.items.removeIf(item ->
                    item.getProduct().getId().equals(productId));

        this.updateTotalPrice();
    }

    public void changeItemQuantity(Long productId, int newQuantity){

        this.status.validateEditable(this.number);

        this.items.stream()
                .filter(item ->
                        item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item -> item.updateQuantity(newQuantity));

        this.updateTotalPrice();
    }

    private void updateTotalPrice() {
        this.totalPrice = this.items.stream()
                .map(item -> item
                        .getUnitPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void updateStatus(OrderStatus status) {
        this.status = this.status.transitionTo(status);
    }

    public void updateNotes(String notes) {

        this.status.validateEditable(this.number);
        this.notes = notes;
    }

}
