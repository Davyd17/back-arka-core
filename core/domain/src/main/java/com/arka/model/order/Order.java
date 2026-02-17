package com.arka.model.order;

import com.arka.model.Company;
import com.arka.model.enums.OrderStatus;
import com.arka.model.enums.OrderType;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
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
    private List<OrderItem> items;

    public void addItems(List<OrderItem> items) {
        this.items.addAll(items);
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
    }

    public void removeItem(OrderItem item) {
        this.items.remove(item);
    }

    public void updateStatus(OrderStatus status) {
        this.status = status;
    }

    public void updateNotes(String notes) {
        this.notes = notes;
    }

}
