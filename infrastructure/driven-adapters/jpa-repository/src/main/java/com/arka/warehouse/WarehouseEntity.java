package com.arka.warehouse;

import com.arka.information.address.AddressEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Builder
@Entity
@Table(name = "warehouses")
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Boolean active;

    @OneToOne
    @JoinColumn(name = "address_id", nullable = false, unique = true)
    private AddressEntity address;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdAt;

}
