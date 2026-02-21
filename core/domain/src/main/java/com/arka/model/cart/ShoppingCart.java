package com.arka.model.cart;

import com.arka.enums.ShoppingCartStatus;
import com.arka.model.information.Contact;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class ShoppingCart {

    private Long id;
    private ShoppingCartStatus status;
    private BigDecimal totalAmount;
    private List<ShoppingCartItem> items;
    private Instant createdAt;
    private Instant updatedAt;
    private Contact customer;
}
