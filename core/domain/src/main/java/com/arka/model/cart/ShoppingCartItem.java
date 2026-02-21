package com.arka.model.cart;

import com.arka.model.product.Product;

import java.math.BigDecimal;
import java.time.Instant;

public class ShoppingCartItem {

    private Long id;
    private int quantity;
    private BigDecimal unitPrice;
    private Instant createdAt;
    private Instant updatedAt;
    private Product product;
}
