package com.arka.cart.item;

import com.arka.cart.ShoppingCartEntity;
import com.arka.product.ProductEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "shopping_cart_items")
@Getter
@Setter
public class ShoppingCartItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Min(value = 0, message = "Quantity must be greater than or equal to 0")
    private int quantity;

    @Column(nullable = false)
    private BigDecimal unitPrice = BigDecimal.ZERO;

    @Column(nullable = false)
    private BigDecimal subTotal = BigDecimal.ZERO;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    @Column(insertable = false)
    private Instant updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopping_cart_id", nullable = false)
    @JsonBackReference
    private ShoppingCartEntity shoppingCart;
}
