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
import java.util.Optional;

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

        validateAddItemArgs(product, quantity);
        validateCartStatusAtAddItem();

        activeCartIfAbandoned();

        getExistingItem(product)
                .ifPresentOrElse(existingItem -> existingItem.addQuantity(quantity),
                        () -> this.items.add(new ShoppingCartItem(product, quantity)));

        updateTotalPrice();
    }

    private Optional<ShoppingCartItem> getExistingItem(Product product) {
        return this.items.stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst();
    }

    private void validateAddItemArgs(Product product, int quantity){
        validateProduct(product);
        validateQuantity(quantity);
    }

    private void validateProduct(Product product) {

        if(product == null)
            throw new IllegalArgumentException("Product can't be null");

        if(!product.isActive())
            throw new IllegalArgumentException("Product must be active");
    }

    private void validateQuantity(int quantity) {
        if(quantity <= 0)
            throw new IllegalArgumentException("Quantity must be greater than 0");
    }

    private void activeCartIfAbandoned() {

        if(this.status.equals(ShoppingCartStatus.ABANDONED))
            this.status = ShoppingCartStatus.ACTIVE;
    }

    private void validateCartStatusAtAddItem() {

        if(this.status.equals(ShoppingCartStatus.PROCESSED) ||
                this.status.equals(ShoppingCartStatus.CANCELLED))

            throw new IllegalArgumentException(
                    String.format("Cart is in state %s, cannot add items", this.status));
    }

    private void updateTotalPrice() {
        this.totalAmount = this.items.stream()
                .map(item -> item
                        .getUnitPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
