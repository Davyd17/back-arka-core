package com.arka.cart;

import com.arka.cart.item.ShoppingCartItemEntity;
import com.arka.enums.ShoppingCartStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "shopping_carts")
@Builder
@Getter
@Setter
public class ShoppingCartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ShoppingCartStatus status = ShoppingCartStatus.ACTIVE;

    @Column(nullable = false)
    private BigDecimal totalAmount;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "shoppingCart" )
    @JsonManagedReference
    private List<ShoppingCartItemEntity> items;

    @Column(nullable = false)
    private Long userId;
}
