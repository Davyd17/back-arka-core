package com.arka.model.cart;

import com.arka.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Builder
@AllArgsConstructor
public class ShoppingCartItem {

    private Long id;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal subTotal;
    private Instant createdAt;
    private Instant updatedAt;
    private final Product product;

    public ShoppingCartItem(Product product, int quantity) {

        this.product = product;
        assignUnitPrice();
        updateSubTotal();

        this.quantity = quantity;
    }

    private void assignUnitPrice() {
        this.unitPrice = product.getBasePrice();
    }

    private void updateSubTotal() {
        this.subTotal =
                this.unitPrice.multiply(BigDecimal.valueOf(this.quantity));
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
        updateSubTotal();
    }

    public void updateQuantity(int quantity) {
        this.quantity = quantity;
        updateSubTotal();
    }

}
