package com.arka.entities.order;

import com.arka.entities.Company;
import com.arka.enums.OrderStatus;
import com.arka.enums.OrderType;
import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    private Company company;
    private List<OrderItem> items;
    private Instant createdAt;
    private Instant updatedAt;

    public static Order create(@Nullable String notes,
                               OrderType type,
                               Company company) {

        return Order.builder()
                .number(generateNumber())
                .status(OrderStatus.PENDING)
                .notes(notes)
                .type(type)
                .company(company)
                .items(new ArrayList<>())
                .build();
    }

    private static String generateNumber(){
        String randomSuffix =
                UUID.randomUUID().toString().substring(0, 4).toUpperCase();

        String formatedDate = LocalDate.now()
                .format(DateTimeFormatter.BASIC_ISO_DATE);

        return "ORD-" + formatedDate + "-" + randomSuffix;
    }

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
                .map(OrderItem::getTotalPrice)
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
