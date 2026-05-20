package com.arka.model.cart;

import com.arka.enums.ShoppingCartStatus;
import com.arka.model.product.Product;
import com.arka.util.NullValidator;
import com.arka.util.QuantityValidator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ShoppingCart {

    private Long id;
    private ShoppingCartStatus status;
    private BigDecimal totalAmount;
    private List<ShoppingCartItem> items;
    private Instant createdAt;
    private Instant updatedAt;
    private final Long userId;

    public static ShoppingCart create(
            Long userId
    ){

        return ShoppingCart.builder()
                .status(ShoppingCartStatus.ACTIVE)
                .items(new ArrayList<>())
                .userId(userId)
                .totalAmount(BigDecimal.ZERO)
                .build();
    }

    public void addItem(Product product, int quantity){

        validateAddItemArgs(product, quantity);

        activeCartIfAbandoned();

        Optional<ShoppingCartItem> item = getExistingItem(product);

        if(item.isPresent()){
            item.get().updateQuantity(quantity);
        } else
            this.items.add(ShoppingCartItem.create(quantity, product));

        updateTotalAmount();
    }

    private Optional<ShoppingCartItem> getExistingItem(Product product) {
        return this.items.stream()
                .filter(item ->
                        item.getProduct().getId().equals(product.getId()))
                .findFirst();
    }

    private void validateAddItemArgs(Product product, int quantity){
        validateProduct(product);
        QuantityValidator.validate(quantity);
        this.status.validateEditable(this.id);
    }

    private void validateProduct(Product product) {

        NullValidator.validate(product, "product");

        if(!product.isActive())
            throw new IllegalStateException("Product must be active");
    }

    private void activeCartIfAbandoned() {

        if(this.status.equals(ShoppingCartStatus.ABANDONED))
            this.status = this.status.transitionTo(ShoppingCartStatus.ACTIVE);
    }

    private void updateTotalAmount() {
        this.totalAmount = this.items.stream()
                .map(ShoppingCartItem::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public boolean isExpired(Duration expirationPeriod){
        return Instant.now().isAfter(this.createdAt.plus(expirationPeriod));
    }

    public boolean isAbandoned(Duration abandonedPeriod){

        if(this.updatedAt == null)
            return Instant.now().isAfter(this.createdAt.plus(abandonedPeriod));

        return Instant.now().isAfter(this.updatedAt.plus(abandonedPeriod));
    }

    public void abandon(){
        this.status = this.status.transitionTo(ShoppingCartStatus.ABANDONED);
    }

    public void cancel(){
        this.status = this.status.transitionTo(ShoppingCartStatus.CANCELLED);
    }
}
