package com.arka.order;

import com.arka.company.CompanyEntity;
import com.arka.enums.OrderStatus;
import com.arka.enums.OrderType;
import com.arka.order.item.OrderItemEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String number;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderType type;

    private String notes;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @Column(nullable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(insertable = false)
    @UpdateTimestamp
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private CompanyEntity company;

    @OneToMany(mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonManagedReference
    private Set<OrderItemEntity> items;
}
