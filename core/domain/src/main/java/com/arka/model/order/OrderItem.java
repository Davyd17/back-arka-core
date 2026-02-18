package com.arka.model.order;

import com.arka.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
public class OrderItem {
    private Long id;
    private Product product;
    private int quantity;
    private BigDecimal unitPrice;

    public void updateQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void addProduct(Product product){

        if(product != null && product.getBasePrice() != null && product.isActive()){

            this.product = product;
            this.setUnitPriceFromProductBasePrice();

        } else throw new IllegalArgumentException(
                "Product must be active and have a base price to be added.");
    }

    private void setUnitPriceFromProductBasePrice() {
        if (Objects.nonNull(product))
            this.unitPrice = product.getBasePrice();
    }
}
