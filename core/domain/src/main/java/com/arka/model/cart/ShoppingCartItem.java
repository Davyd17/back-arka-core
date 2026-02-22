package com.arka.model.cart;

import com.arka.model.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
public class ShoppingCartItem {

    private Long id;
    @Setter
    private int quantity;
    private BigDecimal unitPrice;
    private Instant createdAt;
    private Instant updatedAt;
    private final Product product;

    public ShoppingCartItem(Product product, int quantity) {

        this.product = product;
        assignUnitPrice();

        this.quantity = quantity;
    }

    private void assignUnitPrice() {
        this.unitPrice = product.getBasePrice();
    }

}
