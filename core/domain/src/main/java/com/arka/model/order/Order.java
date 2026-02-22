package com.arka.model.order;

import com.arka.model.Company;
import com.arka.enums.OrderStatus;
import com.arka.enums.OrderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@Builder
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

    public Order() {
        this.status = OrderStatus.PENDING;
    }

    public void addCompany(Company company) {
        this.company = company;
    }

    public void updateItems(List<OrderItem> newItems) {

        this.items.removeIf(currentItem ->
                newItems.stream().noneMatch(newItem ->
                        newItem.getProduct().getId().equals(currentItem.getProduct().getId())));

        for (OrderItem newItem : newItems) {

            this.items.stream()
                    .filter(item ->
                            item.getProduct().getId().equals(newItem.getProduct().getId()))
                    .findFirst()
                    .ifPresentOrElse(existingItem -> {

                        if(existingItem.getQuantity() != newItem.getQuantity())
                            existingItem.updateQuantity(newItem.getQuantity());

                    }, () -> this.items.add(newItem));
        }

        this.updateTotalPrice();
    }

    public void updateTotalPrice() {
        this.totalPrice = this.items.stream()
                .map(item -> item
                        .getUnitPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void updateStatus(OrderStatus status) {
        this.status = status;
    }

    public void updateNotes(String notes) {
        this.notes = notes;
    }

}
