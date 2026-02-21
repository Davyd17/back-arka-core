package com.arka.shippingdetail;

import com.arka.information.address.AddressEntity;
import com.arka.enums.ShippingStatus;
import com.arka.order.OrderEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "shipping_details")
public class ShippingDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String carrier;

    @Column(nullable = false, unique = true)
    private String trackingNumber;

    private String notes;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ShippingStatus status;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(insertable = false)
    private Instant updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "origin_address_id", nullable = false)
    private AddressEntity originAddress;

    @ManyToOne
    @JoinColumn(name = "destination_address_id", nullable = false)
    private AddressEntity destinationAddress;
}
