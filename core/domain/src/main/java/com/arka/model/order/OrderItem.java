package com.arka.model.order;

import com.arka.model.product.Product;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder(toBuilder = true)
public class OrderItem {
    private Long id;
    private Product product;
    private int quantity;
    private BigDecimal unitPrice;

    public void updateQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void updateUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
}
