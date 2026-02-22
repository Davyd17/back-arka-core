package com.arka.model.cart;

import com.arka.enums.ShoppingCartStatus;
import com.arka.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ShoppingCart {

    private Long id;
    private ShoppingCartStatus status;
    private BigDecimal totalAmount;
    private List<ShoppingCartItem> items;
    private Instant createdAt;
    private Instant updatedAt;
    private final Long userId;

    public ShoppingCart(Long userId) {
        this.userId = userId;
        this.status = ShoppingCartStatus.ACTIVE;
        this.items = new ArrayList<>();
    }

    public void addItem(Product product, int quantity){

        this.items.add(new ShoppingCartItem(product, quantity));
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        this.totalAmount = this.items.stream()
                .map(item -> item
                        .getUnitPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
